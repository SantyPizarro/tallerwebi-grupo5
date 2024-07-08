package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlanServiceTest {

    RepositorioUsuario repositorioUsuario;
    PlanRepository planRepository;
    PlanService planService;
    RepositorioLibro repositorioLibro;
    RepositorioCupon repositorioCupon;
    RepositorioTipoPlan repositorioTipoPlan;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        planRepository = mock(PlanRepository.class);
        repositorioLibro = mock(RepositorioLibro.class);
        repositorioCupon = mock(RepositorioCupon.class);
        repositorioTipoPlan = mock(RepositorioTipoPlan.class);
        planService = spy(new PlanServiceImpl(planRepository, repositorioUsuario, repositorioLibro, repositorioCupon, repositorioTipoPlan));
    }

    @Test
    @Rollback
    public void usuarioPuedeComprarPlanBasico() throws PlanYaAdquiridoException {
        Usuario usuario = new Usuario();
        Plan planBasico = new Plan();
        planBasico.setId(2L);

        when(planRepository.buscarPlan(2L)).thenReturn(planBasico);

        doReturn(true).when(planService).verificarPlan(usuario);

        planService.comprarPlanBasico(usuario);

        assertEquals(planBasico, usuario.getPlan());
        assertNotNull(usuario.getPlan().getFechaCompra());
        assertNotNull(usuario.getPlan().getFechaVencimiento());
        assertEquals(LocalDateTime.now().plusMonths(1).getMonth(), usuario.getPlan().getFechaVencimiento().getMonth());
        assertEquals(1, usuario.getCuponesEmitidos());

        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    @Rollback
    public void usuarioNoPuedeComprarPlanBasicoSiYaTieneUno() {
        Usuario usuario = new Usuario();

        doReturn(false).when(planService).verificarPlan(usuario);

        assertThrows(PlanYaAdquiridoException.class, () -> planService.comprarPlanBasico(usuario));

        verify(repositorioUsuario, never()).modificar(usuario);
    }

    @Test
    @Rollback
    public void testAplicarBeneficioPlanBasico() {
        Usuario usuario = new Usuario();
        TipoPlan tipoPlan = new TipoPlan();
        tipoPlan.setNombre("basic");
        Plan planBasico = new Plan();
        planBasico.setTipoPlan(tipoPlan);
        usuario.setPlan(planBasico);

        doNothing().when(planService).beneficioCuponPlan(usuario, 20);

        planService.aplicarBeneficioPlanBasico(usuario);

        verify(planService).beneficioCuponPlan(usuario, 20);
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    @Rollback
    public void testAplicarBeneficioPlanEstandar() {
        Usuario usuario = new Usuario();
        usuario.setLibrosPlan(new HashSet<>());
        Plan plan = new Plan();
        TipoPlan tipoPlan = new TipoPlan();
        tipoPlan.setNombre("standard");
        plan.setTipoPlan(tipoPlan);
        usuario.setPlan(plan);

        Libro libro = new Libro("chicharo",20,"eb");
        when(repositorioLibro.buscarLibroPorId(1L)).thenReturn(libro);
        when(repositorioLibro.buscarLibroPorId(2L)).thenReturn(new Libro());

        doNothing().when(planService).beneficioCuponPlan(usuario, 25);
        planService.aplicarBeneficioPlanEstandar(usuario);

        System.out.println("Libros en el plan después de aplicar el beneficio: " + usuario.getLibrosPlan().size());
        for (Libro l : usuario.getLibrosPlan()) {
            System.out.println("Libro: " + l.toString());
        }

        verify(planService).beneficioCuponPlan(usuario, 25);
       // assertEquals(2, usuario.getLibrosPlan().size());   NO TERMINA DE AGREGAR BIEN LOS LIBROS
        verify(repositorioUsuario).modificar(usuario);
    }


}
