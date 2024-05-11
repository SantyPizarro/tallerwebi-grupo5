package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorLibros {

    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorLibros (ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }


    @GetMapping("/libros")
    public ModelAndView mostrarLibros() {
        ModelAndView modelAndView = new ModelAndView("libros");
        modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
        return modelAndView;
    }

    @GetMapping("/detalle-libro")
    public ModelAndView mostrarDetalleLibro() {

        return new ModelAndView("detalle-libro");
    }
}
