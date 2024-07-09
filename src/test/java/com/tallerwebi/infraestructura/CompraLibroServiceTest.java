package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompraLibroServiceTest {

    private CompraLibroServiceImpl compraLibroService;
    private RepositorioCompra repositorioCompra;
    private RepositorioProductosCompra repositorioProductosCompra;
    private RepositorioUsuario repositorioUsuario;
    private PlanService planService;

    @BeforeEach
    public void setUp() {
        repositorioCompra = mock(RepositorioCompra.class);
        repositorioProductosCompra = mock(RepositorioProductosCompra.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        planService = mock(PlanService.class);
        compraLibroService = new CompraLibroServiceImpl(repositorioCompra, repositorioProductosCompra, repositorioUsuario, planService);
    }

    @Test
    public void registrarCompraUsuario() {
        Usuario usuario = new Usuario();
        Plan plan = new Plan();
        TipoPlan tipoPlan = new TipoPlan();
        tipoPlan.setNombre("premium");
        plan.setTipoPlan(tipoPlan);
        plan.setFechaCompra(LocalDateTime.now().minusDays(1));
        plan.setFechaVencimiento(LocalDateTime.now().plusDays(1));
        usuario.setPlan(plan);
        usuario.setLibrosComprados(new HashSet<>());


        Carrito carrito = new Carrito();
        Set<Libro> libros = new HashSet<>();
        libros.add(new Libro());
        carrito.setLibros(libros);

        compraLibroService.registrarCompra(usuario, carrito);

        verify(repositorioCompra, times(1)).crearCompra(any(Compra.class));
        verify(repositorioProductosCompra, times(1)).crearProducto(any(ProductosCompra.class));
        verify(repositorioUsuario, times(2)).modificar(usuario);
        verify(planService, times(1)).beneficioCuponPlan(eq(usuario), eq(30));
    }




}
