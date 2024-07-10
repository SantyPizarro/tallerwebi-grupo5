package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorCarritoTest {

    private ControladorCarrito controladorCarrito;
    private CarritoService carritoServiceMock;
    private ServicioLibro servicioLibroMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private Carrito carritoMock;
    private Usuario usuarioMock;
    private Cupon cuponMock;
    private Libro libroMock;

    @BeforeEach
    public void init() {
        carritoServiceMock = mock(CarritoService.class);
        servicioLibroMock = mock(ServicioLibro.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        carritoMock = mock(Carrito.class);
        usuarioMock = mock(Usuario.class);
        cuponMock = mock(Cupon.class);
        libroMock = mock(Libro.class);

        controladorCarrito = new ControladorCarrito(carritoServiceMock, servicioLibroMock);

        when(requestMock.getSession()).thenReturn(sessionMock);
    }

    @Test
    public void mostrarLibrosCompradosTest() {
        when(sessionMock.getAttribute("CARRITO")).thenReturn(carritoMock);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("cupon")).thenReturn(cuponMock);
        when(sessionMock.getAttribute("libroAeliminar")).thenReturn(libroMock);

        ModelAndView modelAndView = controladorCarrito.mostrarLibrosComprados(requestMock);

        verify(carritoServiceMock, times(1)).obtenerLibrosComprados(carritoMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("comprar"));
    }

    @Test
    public void guardarLibrosTest() throws LibroNoAgregado {
        Long libroId = 1L;
        when(sessionMock.getAttribute("CARRITO")).thenReturn(carritoMock);
        when(sessionMock.getAttribute("cantidadLibros")).thenReturn(1);

        ModelAndView modelAndView = controladorCarrito.guardarLibros(libroId, requestMock);

        verify(carritoServiceMock, times(1)).agregarLibrosAlCarrito(libroId, carritoMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void eliminarLibroDeCarritoTest() {
        String tituloLibro = "titulo";
        when(sessionMock.getAttribute("CARRITO")).thenReturn(carritoMock);
        when(sessionMock.getAttribute("cantidadLibros")).thenReturn(1);
        when(servicioLibroMock.buscarLibroPorTitulo(tituloLibro)).thenReturn(libroMock);

        ModelAndView modelAndView = controladorCarrito.eliminarLibroDeCarrito(tituloLibro, requestMock);

        verify(carritoServiceMock, times(1)).eliminarLibroDeCarrito(libroMock, carritoMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mostrar-carrito"));
    }

    @Test
    public void darLibroEnFormaDePagoTest() {
        Long libroId = 1L;
        when(sessionMock.getAttribute("CARRITO")).thenReturn(carritoMock);
        when(servicioLibroMock.buscarLibroPorId(libroId)).thenReturn(libroMock);
        when(libroMock.getPrecio()).thenReturn(100.0);
        when(carritoServiceMock.obtenerSubtotal(carritoMock)).thenReturn(150.0);

        ModelAndView modelAndView = controladorCarrito.darLibroEnFormaDePago(libroId, requestMock);

        verify(carritoServiceMock, times(1)).setTotal(carritoMock, 50.0);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mostrar-carrito"));
    }

    @Test
    public void aplicarCuponTest() {
        Long cuponId = 1L;
        when(sessionMock.getAttribute("CARRITO")).thenReturn(carritoMock);
        when(carritoServiceMock.buscarCuponPorId(cuponId)).thenReturn(cuponMock);

        ModelAndView modelAndView = controladorCarrito.aplicarCupon(cuponId, requestMock);

        verify(carritoServiceMock, times(1)).calcularTotalConCupon(carritoMock, cuponMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mostrar-carrito"));
    }
}
