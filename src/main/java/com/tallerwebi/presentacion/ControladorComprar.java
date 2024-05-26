package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.CompraLibroService;
import com.tallerwebi.dominio.Usuario;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorComprar {


    private CompraLibroService compraLibroService;

    @Autowired
    public ControladorComprar (CompraLibroService compraLibroService) {
       this.compraLibroService = compraLibroService;
    }

    @GetMapping("/comprar")
    public ModelAndView comprar(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");
        compraLibroService.registrarCompra(usuario, carrito);

        return new ModelAndView("home");

    }

}


