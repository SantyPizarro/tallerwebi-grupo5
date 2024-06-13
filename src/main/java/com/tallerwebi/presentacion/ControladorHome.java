package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;
    private final ServicioSuscriptor servicioSuscriptor;
    private final ServicioPreferencias servicioPreferencias;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro, ServicioSuscriptor servicioSuscriptor, ServicioPreferencias servicioPreferencias) {
        this.servicioLibro = servicioLibro;
        this.servicioSuscriptor = servicioSuscriptor;
        this.servicioPreferencias = servicioPreferencias;
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
            modelAndView.addObject("librosOrdenados", servicioLibro.ordenarPorFechaAgregado());

            Preferencias preferencias = new Preferencias(usuario);
            List<Libro> librosRecomendados = servicioPreferencias.recomendarLibros(preferencias);
            modelAndView.addObject("librosRecomendados", librosRecomendados);
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");

    }

    @PostMapping("/suscribirse")
    public String agregarSuscriptor(@RequestParam("email") String email, @RequestParam("nombre") String nombre) {
        servicioSuscriptor.agregarSuscriptor(email, nombre);
        return "redirect:/home";
    }

}