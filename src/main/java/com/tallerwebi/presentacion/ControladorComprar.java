package com.tallerwebi.presentacion;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.*;
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

    @Autowired
    public ControladorComprar (CompraLibroService compraLibroService,MercadoPagoService mercadoPagoService, CarritoService carritoService) {
        this.mercadoPagoService = mercadoPagoService;
        this.compraLibroService = compraLibroService;
        this.carritoService = carritoService;
    }
    @PostMapping("/comprar")
    public ModelAndView comprar(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");

        Double totalCarrito = carritoService.obtenerSubtotal(carrito);

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

        if ("approved".equals(paymentStatus)) {
            compraLibroService.registrarCompra(usuario, carrito);

            carrito.limpiar();
            sesion.setAttribute("cantidadLibros", 0);
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/mostrar-carrito");
    }
}