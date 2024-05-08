package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.excepcion.CompraLibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorComprar {

    @Autowired
    private CompraLibroService libroService;


    @GetMapping("/comprar")
    public ModelAndView comprar(@RequestParam String titulo) {
        ModelMap model = new ModelMap();
        Double subtotal = libroService.sumarSubtotal(titulo);
        model.put("subtotal", subtotal);
        return new ModelAndView("comprar", model);
    }



}
