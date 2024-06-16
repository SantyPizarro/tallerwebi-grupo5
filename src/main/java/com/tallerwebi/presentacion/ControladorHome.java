package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.NotificacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;
    private final SolicitudAmistadService solicitudAmistadService;
    private final NotificacionService notificacionService;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro, SolicitudAmistadService solicitudAmistadService, NotificacionService notificacionService) {
        this.servicioLibro = servicioLibro;
        this.solicitudAmistadService = solicitudAmistadService;
        this.notificacionService = notificacionService;
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("libros", servicioLibro.obtenerTodosLosLibros());
            modelAndView.addObject("librosOrdenados", servicioLibro.ordenarPorFechaAgregado());
            modelAndView.addObject("cantidadNotificaciones", notificacionService.cantidadDeNotificaciones(usuario));
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");

    }

}