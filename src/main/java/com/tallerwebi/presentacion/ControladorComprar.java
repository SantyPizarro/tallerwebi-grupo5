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
    private final RepositorioCupon repositorioCupon;

    @Autowired
    public ControladorComprar (CompraLibroService compraLibroService, MercadoPagoService mercadoPagoService, CarritoService carritoService, RepositorioUsuario repositorioUsuario,RepositorioCupon repositorioCupon) {
        this.mercadoPagoService = mercadoPagoService;
        this.compraLibroService = compraLibroService;
        this.carritoService = carritoService;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCupon = repositorioCupon;
    }
    @PostMapping("/comprar")
    public ModelAndView comprar(HttpServletRequest request,@RequestParam("precioFinal") Double precioFinal ) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");

        Preference preference = mercadoPagoService.createPreference(usuario, precioFinal);

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
        Cupon cupon = (Cupon) sesion.getAttribute("cupon");

        if ("approved".equals(paymentStatus)) {
            compraLibroService.registrarCompra(usuario, carrito);

            if(libroAeliminar != null){
                usuario.getLibrosComprados().remove(libroAeliminar);
                repositorioUsuario.modificar(usuario);
                sesion.setAttribute("libroAeliminar", null);
            }

            if(cupon != null){
                usuario.getCuponesDeDescuento().remove(cupon);
                repositorioUsuario.modificar(usuario);
                CodigoDescuento codigoDescuento = repositorioCupon.buscarCodigoDescuento(1L);
                codigoDescuento.getCuponesDescuento().remove(cupon);
                repositorioCupon.modificarCodigoDescuento(codigoDescuento);
                repositorioCupon.eliminarCupon(cupon);
                sesion.setAttribute("cupon", null);
            }

            carrito.limpiar();
            sesion.setAttribute("cantidadLibros", 0);

            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/mostrar-carrito");
    }
}