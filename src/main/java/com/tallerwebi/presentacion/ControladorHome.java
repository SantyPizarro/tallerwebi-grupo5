package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
        return modelAndView;
    }
}