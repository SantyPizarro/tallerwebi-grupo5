package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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




    @GetMapping("/filtrar-libros")
    public ModelAndView mostrarLibrosPorEditorial(@RequestParam("editorial") String editorial) {
        List<Libro> librosFiltrados = servicioLibro.filtrarPorEditorial(editorial);
        ModelAndView modelAndView = new ModelAndView("libros-filtrar");
        modelAndView.addObject("librosFiltrados", librosFiltrados);
        modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
        return modelAndView;
        
    }


    @GetMapping("/detalle-libro")
    public ModelAndView mostrarDetalleLibro() {

        return new ModelAndView("detalle-libro");
    }
}
