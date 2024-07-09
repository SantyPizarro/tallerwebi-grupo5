package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;


public class ControladorSolicitudAmistadTest {

    private ControladorSolicitudAmistad controladorSolicitudAmistad;
    private UsuarioService usuarioServiceMock;
    private SolicitudAmistadService solicitudAmistadServiceMock;
    private IntercambioService intercambioServiceMock;
    private NotificacionService notificacionServiceMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;
    private RedirectAttributes redirectAttributesMock;

    @BeforeEach
    public void init() {
        usuarioServiceMock = mock(UsuarioService.class);
        solicitudAmistadServiceMock = mock(SolicitudAmistadService.class);
        intercambioServiceMock = mock(IntercambioService.class);
        notificacionServiceMock = mock(NotificacionService.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
        controladorSolicitudAmistad = new ControladorSolicitudAmistad(usuarioServiceMock, solicitudAmistadServiceMock, intercambioServiceMock, notificacionServiceMock);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    }

    @Test
    public void enviarSolicitudTest() {
        Usuario solicitadoMock = mock(Usuario.class);
        Long idAmigo = 1L;

        when(usuarioServiceMock.buscarPorId(idAmigo)).thenReturn(solicitadoMock);
        when(solicitudAmistadServiceMock.comprobarSolicitudPendiente(solicitadoMock, usuarioMock)).thenReturn(false);

        ModelAndView modelAndView = controladorSolicitudAmistad.enviarSolicitud(requestMock, idAmigo);

        verify(solicitudAmistadServiceMock, times(1)).enviarSolicitud(usuarioMock, solicitadoMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mostrarAmigos"));
    }

    @Test
    public void aceptarNotificacionTest() {
        Long idNotificacion = 1L;
        String tipoNotificacion = "tipo";

        ModelAndView modelAndView = controladorSolicitudAmistad.aceptarNotificacion(requestMock, idNotificacion, tipoNotificacion);

        verify(notificacionServiceMock, times(1)).aceptarNotificacion(tipoNotificacion, idNotificacion);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/solicitud-amistad"));
    }

    @Test
    public void rechazarNotificacionTest() {
        Long idNotificacion = 1L;
        String tipoNotificacion = "tipo";

        ModelAndView modelAndView = controladorSolicitudAmistad.rechazarNotificacion(requestMock, idNotificacion, tipoNotificacion);

        verify(notificacionServiceMock, times(1)).rechazarSolicitud(tipoNotificacion, idNotificacion);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/solicitud-amistad"));
    }

    @Test
    public void irANotificacionesRedirectToLoginWhenUserIsNullTest() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);

        ModelAndView modelAndView = controladorSolicitudAmistad.irANotificaciones(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void enviarSolicitudRedirectToLoginWhenUserIsNullTest() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        Long idAmigo = 1L;

        ModelAndView modelAndView = controladorSolicitudAmistad.enviarSolicitud(requestMock, idAmigo);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void aceptarNotificacionRedirectToLoginWhenUserIsNullTest() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        Long idNotificacion = 1L;
        String tipoNotificacion = "tipo";

        ModelAndView modelAndView = controladorSolicitudAmistad.aceptarNotificacion(requestMock, idNotificacion, tipoNotificacion);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void rechazarNotificacionRedirectToLoginWhenUserIsNullTest() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        Long idNotificacion = 1L;
        String tipoNotificacion = "tipo";

        ModelAndView modelAndView = controladorSolicitudAmistad.rechazarNotificacion(requestMock, idNotificacion, tipoNotificacion);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

}
