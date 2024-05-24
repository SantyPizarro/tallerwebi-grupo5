package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioBarraBusqueda;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ControladorLibros {

    private final ServicioLibro servicioLibro;
    private final ServicioBarraBusqueda servicioBarraBusqueda;

    @Autowired
    public ControladorLibros(ServicioLibro servicioLibro, ServicioBarraBusqueda servicioBarraBusqueda) {
        this.servicioLibro = servicioLibro;
        this.servicioBarraBusqueda = servicioBarraBusqueda;
    }

    @GetMapping("/libros")
    public ModelAndView mostrarLibros(
            @RequestParam(value = "editorial", required = false) String editorial,
            @RequestParam(value = "precio-min", required = false) Double precioMinimo,
            @RequestParam(value = "precio-max", required = false) Double precioMaximo)
            {

        List<Libro> libros = servicioLibro.obtenerTodosLosLibros();

        // Filtrado por editorial
        if (editorial != null && !editorial.isEmpty()) {
            libros = libros.stream()
                    .filter(libro -> libro.getEditorial().equalsIgnoreCase(editorial))
                    .collect(Collectors.toList());
        }

        // Filtrado por precio
        if (precioMinimo != null && precioMaximo != null) {
            libros = libros.stream()
                    .filter(libro -> libro.getPrecio() >= precioMinimo && libro.getPrecio() <= precioMaximo)
                    .collect(Collectors.toList());
        }

        Set<String> editoriales = libros.stream()
                .map(Libro::getEditorial)
                .collect(Collectors.toSet());

        ModelAndView modelAndView = new ModelAndView("libros");
        modelAndView.addObject("libros", libros);
        modelAndView.addObject("editoriales", editoriales);
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
