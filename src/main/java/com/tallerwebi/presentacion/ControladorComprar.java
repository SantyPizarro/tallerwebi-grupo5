package com.tallerwebi.presentacion;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.UsuarioServiceImpl;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class ControladorComprar {

    private final CarritoService carritoService;
    private final CompraLibroService compraLibroService;
    private final MercadoPagoService mercadoPagoService;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ControladorComprar (CompraLibroService compraLibroService, MercadoPagoService mercadoPagoService, CarritoService carritoService, RepositorioUsuario repositorioUsuario) {
        this.mercadoPagoService = mercadoPagoService;
        this.compraLibroService = compraLibroService;
        this.carritoService = carritoService;
        this.repositorioUsuario = repositorioUsuario;
    }
    @PostMapping("/comprar")
    public ModelAndView comprar(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");

        Double totalCarrito = carritoService.obtenerTotal(carrito);

        Preference preference = mercadoPagoService.createPreference(usuario, totalCarrito);

        if (preference != null){
            return new ModelAndView("redirect:" + preference.getSandboxInitPoint());
        }

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/payment/feedback")
    public ModelAndView feedback(@RequestParam("collection_status") String paymentStatus,
                                 HttpServletRequest request) {

        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");
        Libro libroAeliminar = (Libro) sesion.getAttribute("libroAeliminar");

        if ("approved".equals(paymentStatus)) {
            compraLibroService.registrarCompra(usuario, carrito);

            if(libroAeliminar != null){
                usuario.getLibrosComprados().remove(libroAeliminar);
                repositorioUsuario.modificar(usuario);
                sesion.setAttribute("libroAeliminar", null);
            }
            carrito.limpiar();
            sesion.setAttribute("cantidadLibros", 0);

            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/mostrar-carrito");
    }
}