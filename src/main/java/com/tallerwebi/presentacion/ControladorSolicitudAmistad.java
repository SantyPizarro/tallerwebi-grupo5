package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.SolicitudAmistadService;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioService;
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
        }
        return new ModelAndView("notificaciones", modelo);
    }

    @PostMapping("/enviar-solicitud")
    public ModelAndView enviarSolicitud(HttpServletRequest request, @RequestParam("idAmigo") Long idAmigo) {
        HttpSession session = request.getSession();
        Usuario solicitante = (Usuario) session.getAttribute("USUARIO");

        if (solicitante != null) {
            Usuario solicitado = usuarioService.buscarPorId(idAmigo);
            if (solicitado != null) {
                solicitudAmistadService.enviarSolicitud(solicitante, solicitado);
            }
        }
        return new ModelAndView("redirect:/mostrarAmigos");
    }


}
