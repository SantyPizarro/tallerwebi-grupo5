package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import com.tallerwebi.infraestructura.PlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IntercambioServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositorioUsuario repositorioUsuario;
    private PlanRepository planRepository;
    private PlanService planService;
    private RepositorioLibro repositorioLibro;
    private RepositorioCupon repositorioCupon;
    private RepositorioTipoPlan repositorioTipoPlan;
    private IntercambioService intercambioService;
    private RepositorioIntercambio repositorioIntercambio;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        planRepository = mock(PlanRepository.class);
        repositorioLibro = mock(RepositorioLibro.class);
        repositorioCupon = mock(RepositorioCupon.class);
        repositorioTipoPlan = mock(RepositorioTipoPlan.class);
        intercambioService = mock(IntercambioService.class);
        repositorioIntercambio = mock(RepositorioIntercambio.class);
        planService = spy(new PlanServiceImpl(planRepository, repositorioUsuario, repositorioLibro, repositorioCupon, repositorioTipoPlan));
    }

    @Test
    @Transactional
    public void usuarioPuedaIntercambiarUnLibroConOtroUsuario() throws PlanYaAdquiridoException {
        // Crear dos usuarios
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario1.setLibrosComprados(new HashSet<>());
        usuario2.setLibrosComprados(new HashSet<>());

        Plan planBasico = new Plan();
        planBasico.setId(2L);
        when(planRepository.buscarPlan(2L)).thenReturn(planBasico);

        // Simular que ambos usuarios tienen el plan básico
        doReturn(true).when(planService).verificarPlan(any(Usuario.class));

        // Realizar la compra del plan básico para ambos usuarios
        planService.comprarPlanBasico(usuario1);
        planService.comprarPlanBasico(usuario2);

        // Configurar libros y realizar el intercambio
        Libro libroUsuario1 = new Libro("Martín Fierro", 20, "eb");
        Libro libroUsuario2 = new Libro("El Principito", 20, "eb");
        usuario1.getLibrosComprados().add(libroUsuario1);
        usuario2.getLibrosComprados().add(libroUsuario2);

        intercambioService.enviarOfertaIntercambio(usuario1, libroUsuario1, usuario2, libroUsuario2);

        // Aceptar la oferta de intercambio
        OfertaIntercambio oferta = repositorioIntercambio.buscarOfertaPorId(1L);
        repositorioIntercambio.aceptarOferta(oferta);

        // Verificación de resultados esperados
        assertTrue(usuario1.getLibrosComprados().contains(libroUsuario2));
        assertTrue(usuario2.getLibrosComprados().contains(libroUsuario1));

        // Verificación de interacciones con el repositorio de usuarios
        verify(repositorioUsuario, times(2)).modificar(any(Usuario.class)); // Se esperan 2 modificaciones
    }
}
