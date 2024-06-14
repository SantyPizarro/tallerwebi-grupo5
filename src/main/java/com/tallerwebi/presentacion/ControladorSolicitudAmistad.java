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
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ControladorSolicitudAmistad {

    private UsuarioService usuarioService;
    private SolicitudAmistadService solicitudAmistadService;
    private IntercambioService intercambioService;
    private NotificacionService notificacionService;

    @Autowired
    public ControladorSolicitudAmistad(UsuarioService usuarioService, SolicitudAmistadService solicitudAmistadService, IntercambioService intercambioService, NotificacionService notificacionService) {
        this.usuarioService = usuarioService;
        this.solicitudAmistadService = solicitudAmistadService;
        this.intercambioService = intercambioService;
        this.notificacionService = notificacionService;
    }

    @RequestMapping("/solicitud-amistad")
    public ModelAndView irANotificaciones(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        ModelMap modelo = new ModelMap();

        List<OfertaIntercambio> ofertaIntercambios = new ArrayList<>(intercambioService.buscarOfertas(usuario));
        List<SolicitudAmistad> solicitudesDeAmistad = new ArrayList<>(solicitudAmistadService.buscarSolicitudes(usuario));

        List<Object> listaCombinada = new ArrayList<>();

        if(usuario != null){
            listaCombinada.addAll(solicitudesDeAmistad);
            listaCombinada.addAll(ofertaIntercambios);

            listaCombinada.sort((o1, o2) -> {
                LocalDateTime fecha1 = null;
                LocalDateTime fecha2 = null;

                if (o1 instanceof SolicitudAmistad) {
                    fecha1 = ((SolicitudAmistad) o1).getFecha();
                } else if (o1 instanceof OfertaIntercambio) {
                    fecha1 = ((OfertaIntercambio) o1).getFecha();
                }

                if (o2 instanceof SolicitudAmistad) {
                    fecha2 = ((SolicitudAmistad) o2).getFecha();
                } else if (o2 instanceof OfertaIntercambio) {
                    fecha2 = ((OfertaIntercambio) o2).getFecha();
                }

                return fecha1.compareTo(fecha2);
            });

            modelo.put("datosLogin", new DatosLogin());

            modelo.put("notificacion", listaCombinada);
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

    @PostMapping("/aceptar-notificacion")
    public ModelAndView aceptarNotificacion(HttpServletRequest request, @RequestParam("notificacion") Long idNotificacion, @RequestParam("tipoSolictud") String tipoNotificacion ) {
        HttpSession session = request.getSession();
        Usuario aceptante = (Usuario) session.getAttribute("USUARIO");

        if (aceptante != null) {
            notificacionService.aceptarNotificacion(tipoNotificacion, idNotificacion);

            usuarioService.actualizarUser(aceptante);

            session.setAttribute("USUARIO", aceptante); //CHECKEAR

            return new ModelAndView("redirect:/solicitud-amistad");
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
