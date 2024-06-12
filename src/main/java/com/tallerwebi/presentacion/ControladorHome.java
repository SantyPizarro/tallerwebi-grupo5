package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSuscriptor;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;
    private final ServicioSuscriptor servicioSuscriptor;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro, ServicioSuscriptor servicioSuscriptor) {
        this.servicioLibro = servicioLibro;
        this.servicioSuscriptor = servicioSuscriptor;
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

    @PostMapping("/suscribirse")
    public String agregarSuscriptor(@RequestParam("email") String email, @RequestParam("nombre") String nombre) {
        servicioSuscriptor.agregarSuscriptor(email, nombre);
        return "redirect:/home";
    }

}