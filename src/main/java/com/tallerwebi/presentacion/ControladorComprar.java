package com.tallerwebi.presentacion;

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


    private CompraLibroService compraLibroService;
    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorComprar (CompraLibroService compraLibroService,ServicioMercadoPago servicioMercadoPago) {
        this.servicioMercadoPago = servicioMercadoPago;
        this.compraLibroService = compraLibroService;
    }

    @PostMapping("/comprar")
    public ModelAndView comprar(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");


        Preference preference = servicioMercadoPago.createPreference(usuario, carrito); // trycatch

        if (preference != null){
            compraLibroService.registrarCompra(usuario, carrito);

            carrito.limpiar();
            sesion.setAttribute("cantidadLibros", 0);
            return new ModelAndView("redirect:" + preference.getSandboxInitPoint());
        }

        return new ModelAndView("redirect:/home");
    }


}