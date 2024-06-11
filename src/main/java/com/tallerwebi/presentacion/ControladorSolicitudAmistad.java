package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorSolicitudAmistad {

    private UsuarioService usuarioService;
    private SolicitudAmistadService solicitudAmistadService;

    @Autowired
    public ControladorSolicitudAmistad(UsuarioService usuarioService, SolicitudAmistadService solicitudAmistadService) {
        this.usuarioService = usuarioService;
        this.solicitudAmistadService = solicitudAmistadService;
    }

    @RequestMapping("/solicitud-amistad")
    public ModelAndView irANotificaciones(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        if(usuario != null){
            modelo.put("solicitudes", solicitudAmistadService.buscarSolicitudes(usuario));
            return new ModelAndView("notificaciones", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/enviar-solicitud")
    public ModelAndView enviarSolicitud(HttpServletRequest request, @RequestParam("idAmigo") Long idAmigo) {
        HttpSession session = request.getSession();
        Usuario solicitante = (Usuario) session.getAttribute("USUARIO");

        if (solicitante != null) {
            Usuario solicitado = usuarioService.buscarPorId(idAmigo);
            if (solicitado != null && !solicitudAmistadService.comprobarSolicitudPendiente(solicitado, solicitante)) {
                solicitudAmistadService.enviarSolicitud(solicitante, solicitado);
                return new ModelAndView("redirect:/mostrarAmigos");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/aceptar-solicitud")
    public ModelAndView aceptarSolicitud(HttpServletRequest request, @RequestParam("solicitante") Long idAmigo) {
        HttpSession session = request.getSession();
        Usuario aceptante = (Usuario) session.getAttribute("USUARIO");

        if (aceptante != null) {
            Usuario solicitante = usuarioService.buscarPorId(idAmigo);
            if (solicitante != null) {
                solicitudAmistadService.aceptarSolicitud(aceptante, solicitante);
                return new ModelAndView("redirect:/solicitud-amistad");
            }
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/rechazar-solicitud")
    public ModelAndView rechazarSolicitud(HttpServletRequest request, @RequestParam("idSolicitud") Long idSolicitud) {
        HttpSession session = request.getSession();
        Usuario aceptante = (Usuario) session.getAttribute("USUARIO");

        if (aceptante != null ) {
            SolicitudAmistad solicitud = solicitudAmistadService.buscarPorId(idSolicitud);
            if (solicitud != null){
                solicitudAmistadService.rechazarSolicitud(solicitud);
                return new ModelAndView("redirect:/solicitud-amistad");
            }
        }
        return new ModelAndView("redirect:/login");
    }
}