package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;
	private DatosRegistro datosRegistroMock;

	@BeforeEach
	public void init(){
		datosLoginMock = new DatosLogin("test@unlam.edu.ar", "test");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
		datosRegistroMock = mock(DatosRegistro.class);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario no encontrado"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}

	@Test
	public void loginAdministradorConUsuarioYPasswordCorrectosDeberiaLLevarAPerfilAdmin(){
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");
		when(usuarioEncontradoMock.getEmail()).thenReturn("test@unlam.edu.ar");
		when(usuarioEncontradoMock.getPassword()).thenReturn("test");


		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString())).thenReturn(usuarioEncontradoMock);
		
		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/perfilAdmin"));
		verify(sessionMock, times(1)).setAttribute("nombreUsuario", usuarioEncontradoMock.getNombreDeUsuario());
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYMostrarPantallaDeCodigoDeVerificacion() throws UsuarioExistente, NoCoincideContrasenia {

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("codigoDeVerificacion"));
		verify(servicioLoginMock, times(1)).registrar(datosRegistroMock);
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, NoCoincideContrasenia {
		// preparacion
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(datosRegistroMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, NoCoincideContrasenia {
		// preparacion
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(datosRegistroMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(datosRegistroMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}
}
