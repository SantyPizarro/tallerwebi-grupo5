package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorPerfilTest {


    private ControladorPerfil controladorPerfil;
    private Usuario usuarioMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private PerfilService servicioPerfilMock;
    private ServicioLibro servicioLibroMock;
    private PlanService servicioPlanMock;
    private RedirectAttributes redirectAttributesMock;


    @BeforeEach
    public void init(){
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioPerfilMock = mock(PerfilService.class);
        servicioLibroMock = mock(ServicioLibro.class);
        servicioPlanMock = mock(PlanService.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
        controladorPerfil = new ControladorPerfil(servicioPerfilMock,servicioLibroMock,servicioPlanMock);
    }



    @Test
    public void usuarioPuedeVerSuPerfil(){
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);

        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioEncontradoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);

        // ejecucion
        ModelAndView modelAndView = controladorPerfil.mostrarPerfil(requestMock,null,null);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
    }

    @Test
    public void usuarioPuedeModificarSuPerfil() {
        // Preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        Usuario usuarioNuevo = new Usuario();
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioEncontradoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        // Ejecución
        String redirectView = controladorPerfil.editarPerfilCompleto(null,usuarioNuevo,redirectAttributesMock,requestMock);

        // Validación
        assertThat(redirectView, is("redirect:/perfil"));
        verify(redirectAttributesMock).addFlashAttribute("success", "Usuario modificado");
    }

    @Test
    public void usuarioNoEncontradoEnSesion() {
        // Preparación
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        when(requestMock.getSession()).thenReturn(sessionMock);

        // Ejecución
        String redirectView = controladorPerfil.editarPerfilCompleto(null, new Usuario(), redirectAttributesMock, requestMock);

        // Validación
        assertThat(redirectView, is("redirect:/perfil"));
        verify(redirectAttributesMock).addFlashAttribute("error", "Usuario no encontrado");
    }

    @Test
    public void errorAlGuardarLaFoto() {
        // Preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        Usuario usuarioNuevo = new Usuario();
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioEncontradoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        doThrow(new RuntimeException("Error al guardar la foto")).when(servicioPerfilMock).editarPerfilCompleto(usuarioEncontradoMock, usuarioNuevo, null);

        // Ejecución
        String redirectView = controladorPerfil.editarPerfilCompleto(null, usuarioNuevo, redirectAttributesMock, requestMock);

        // Validación
        assertThat(redirectView, is("redirect:/login"));
        verify(redirectAttributesMock).addFlashAttribute("error", "Error al guardar la foto: Error al guardar la foto");
    }

    @Test
    public void unUsuarioPuedeAgregarUnLibroComoFavorito() throws LibroExistente {
        // Preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        Libro libroMock = mock(Libro.class);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioEncontradoMock);
        when(servicioLibroMock.mostrarDetalleLibro("el principito")).thenReturn(libroMock);

        // Ejecución
        String redirectView = controladorPerfil.agregarLibroFavorito("el principito", requestMock, redirectAttributesMock);

        // Validación
        assertThat(redirectView, is("redirect:/perfil"));
        verify(servicioPerfilMock).addLibroFavorito(usuarioEncontradoMock, libroMock);
    }

    @Test
    public void noSePuedeAgregarUnLibroExistenteComoFavorito() throws LibroExistente {
        // Preparación
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        Libro libroMock = mock(Libro.class);

        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioEncontradoMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servicioLibroMock.mostrarDetalleLibro("el principito")).thenReturn(libroMock);
        doThrow(new LibroExistente()).when(servicioPerfilMock).addLibroFavorito(usuarioEncontradoMock, libroMock);

        // Ejecución
        String redirectView = controladorPerfil.agregarLibroFavorito("el principito", requestMock, redirectAttributesMock);

        // Validación
        assertThat(redirectView, is("redirect:/perfil"));
        verify(redirectAttributesMock).addFlashAttribute("errorLibroFavorito", "No se puede añadir un libro como favorito si ya existe en la lista");
    }
}
