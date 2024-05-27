package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/filtrar-precio")
    public ModelAndView mostrarLibrosPorPrecio(@RequestParam("precio-min") double precioMinimo, @RequestParam("precio-max") double precioMaximo){
        List<Libro> librosFiltrados = servicioLibro.filtrarPorPrecio(precioMinimo, precioMaximo);
        ModelAndView modelAndView = new ModelAndView("libros-precio");
        modelAndView.addObject("librosFiltrados", librosFiltrados);
        modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
        return modelAndView;
    }

//    @PostMapping("/detalle-libro")
//    public ModelAndView mostrarDetalleLibro(@ModelAttribute("libro") Libro libro) {
//        Libro libroMostrar = servicioLibro.mostrarDetalleLibro(libro);
//
//        ModelMap modelo = new ModelMap();
//        modelo.put("libro",libroMostrar);
//
//        return new ModelAndView("detalle-libro", modelo);
//    }

    @PostMapping("/detalle-libro")
    public String detalleLibro(@RequestParam("titulo") String titulo, Model model) {
        Libro libro = servicioLibro.mostrarDetalleLibro(titulo);
        if (libro != null) {
            model.addAttribute("libro", libro);
            return "detalle-libro";
        } else {
            // Manejar el caso en que no se encuentre el libro
            return "error";
        }
    }
}
