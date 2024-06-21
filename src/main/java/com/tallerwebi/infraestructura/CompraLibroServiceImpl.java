package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private RepositorioCompra repositorioCompra;
    private RepositorioProductosCompra repositorioProductosCompra;
    private RepositorioUsuario repositorioUsuario;
    private PlanService planService;

    @Autowired
    public CompraLibroServiceImpl(RepositorioCompra repositorioCompra, RepositorioProductosCompra repositorioProductosCompra, RepositorioUsuario repositorioUsuario, PlanService planService) {
        this.repositorioCompra = repositorioCompra;
        this.repositorioProductosCompra = repositorioProductosCompra;
        this.repositorioUsuario = repositorioUsuario;
        this.planService = planService;
    }

    @Override
    public void registrarCompra(Usuario usuario, Carrito carrito) {
        Compra compra = new Compra(usuario);
        repositorioCompra.crearCompra(compra);
        Set<Libro> librosComprados = carrito.getLibros();

        for (Libro libros : librosComprados) {
            ProductosCompra productos = new ProductosCompra(compra);
            productos.setLibro(libros);
            repositorioProductosCompra.crearProducto(productos);
            usuario.setLibrosComprados(libros);
            repositorioUsuario.modificar(usuario);
        }

        if (usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("standard") || usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("premium")) {
            LocalDateTime fechaCompraPlan = usuario.getPlan().getFechaCompra();
            LocalDateTime fechaVencimientoPlan = usuario.getPlan().getFechaVencimiento();
            LocalDateTime fechaDeCompra = compra.getFechaDeCompra();

            // Asegurarse de que las fechas no sean nulas
            if (fechaCompraPlan != null && fechaVencimientoPlan != null && fechaDeCompra != null) {
                if (fechaCompraPlan.isBefore(fechaDeCompra) && fechaVencimientoPlan.isAfter(fechaDeCompra)) {
                    cuponCadaDosCompras(usuario, fechaCompraPlan);
                    repositorioUsuario.modificar(usuario);
                }
            }
        }
    }



    @Override
    public void cuponCadaDosCompras(Usuario usuario, LocalDateTime fechaCompraPlan) {
        Integer totalCompras = repositorioUsuario.cantidadDeCompras(usuario, fechaCompraPlan);

        Integer cuponesNuevos = totalCompras / 2;

        Integer cuponesEmitidos = usuario.getCuponesEmitidos() - 1;

        Integer cuponesAAgregar = Math.max(cuponesNuevos - cuponesEmitidos, 0);

        for (int i = 0; i < cuponesAAgregar; i++) {
            planService.beneficioCuponPlan(usuario, 30);
        }
    }

}
