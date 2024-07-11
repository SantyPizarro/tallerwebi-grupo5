package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioSuscriptor;
import com.tallerwebi.dominio.ServicioCorreo;
import com.tallerwebi.dominio.ServicioSuscriptor;
import com.tallerwebi.dominio.Suscriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControladorNewsletterTest {

    @Mock
    private RepositorioSuscriptor repositorioSuscriptor;

    @Mock
    private ServicioCorreo servicioCorreo;

    private ServicioSuscriptor servicioSuscriptor;

    private ControladorNewsletter controladorNewsletter;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        servicioSuscriptor = new ServicioSuscriptor(repositorioSuscriptor);
        controladorNewsletter = new ControladorNewsletter(servicioCorreo, servicioSuscriptor);

        mockMvc = MockMvcBuilders.standaloneSetup(controladorNewsletter).build();
    }

    @Test
    public void testEnviarNewsletter() {

        Suscriptor suscriptor1 = new Suscriptor("juan1@test.com", "juan1");
        Suscriptor suscriptor2 = new Suscriptor("juan2@test.com", "juan2");
        List<Suscriptor> suscriptores = Arrays.asList(suscriptor1, suscriptor2);

        when(repositorioSuscriptor.obtenerTodosLosSucriptores()).thenReturn(suscriptores);

        ModelAndView modelAndView = controladorNewsletter.enviarNewsletter("Tema", "Mensaje");

        verify(repositorioSuscriptor, times(1)).obtenerTodosLosSucriptores();
        verify(servicioCorreo, times(1)).enviarCorreo("juan1@test.com", "Tema", "Hola juan1, Mensaje");
        verify(servicioCorreo, times(1)).enviarCorreo("juan2@test.com", "Tema", "Hola juan2, Mensaje");

        assertEquals("redirect:/home", modelAndView.getViewName());
    }
}
