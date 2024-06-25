package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioBarraBusqueda;
import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


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
            @RequestParam(value = "precio-max", required = false) Double precioMaximo,
            @RequestParam(value = "genero", required = false) String genero,
            HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {

            List<Libro> libros = servicioLibro.filtrarLibros(editorial, precioMinimo, precioMaximo, genero);
            List<String> editoriales = servicioLibro.obtenerEditoriales();
            List<String> generos = servicioLibro.obtenerGeneros();

            ModelAndView modelAndView = new ModelAndView("libros");
            modelAndView.addObject("libros", libros);
            modelAndView.addObject("editoriales", editoriales);
            modelAndView.addObject("generos", generos);
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
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
    public String detalleLibro(@RequestParam("titulo") String titulo, Model model,HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {

            Libro libro = servicioLibro.mostrarDetalleLibro(titulo);
            if (libro != null) {
                model.addAttribute("libro", libro);
                model.addAttribute("titulo", titulo);
                return "detalle-libro";
            } else {
                return "error";
            }
        }
        return "redirect:/login";
    }
}
