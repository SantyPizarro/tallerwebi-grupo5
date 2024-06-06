package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
            modelAndView.addObject("librosOrdenados", servicioLibro.ordenarPorFechaAgregado());
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");

    }

}