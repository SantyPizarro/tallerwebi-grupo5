package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.CarritoService;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorCarrito {


    private final CarritoService carritoService;

    @Autowired
    public ControladorCarrito(CarritoService carritoService) {
        this.carritoService = carritoService;
    }
    @PostMapping("/carrito")
    public ModelAndView guardarLibros(@RequestParam("id") Long id) {
        try {
            carritoService.agregarLibrosAlCarrito(id);
        } catch (LibroNoAgregado e) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("error", "No se pudo agregar el libro al carrito. Por favor, inténtelo de nuevo más tarde.");
            return modelAndView;
        }
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/mostrar-carrito")
    public ModelAndView mostrarLibrosComprados(){
        List<Libro> librosComprados = carritoService.obtenerLibrosComprados();
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("librosComprados", librosComprados);
        return new ModelAndView ("comprar", modelo);
    }
}
