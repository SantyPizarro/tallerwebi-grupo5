package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioBarraBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorBuscar {

    private final ServicioBarraBusqueda servicioBarraBusqueda;

    @Autowired
    public ControladorBuscar(ServicioBarraBusqueda servicioBarraBusqueda) {
        this.servicioBarraBusqueda = servicioBarraBusqueda;
    }

    @GetMapping("buscar-libro")
    public ModelAndView buscarLibro(@RequestParam("titulo") String titulo) {
        ModelAndView modelAndView = new ModelAndView("libros-buscar");
        List<Libro> librosEncontrados = servicioBarraBusqueda.buscarPorTitulo(titulo);
        modelAndView.addObject("librosEncontrados", librosEncontrados);
        return modelAndView;
    }
}