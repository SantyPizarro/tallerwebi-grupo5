package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private RepositorioCompra repositorioCompra;
    private RepositorioProductosCompra repositorioProductosCompra;
    private RepositorioUsuario repositorioUsuario;
    private PlanService planService;

    @Autowired
    public CompraLibroServiceImpl (RepositorioCompra repositorioCompra, RepositorioProductosCompra repositorioProductosCompra,RepositorioUsuario repositorioUsuario, PlanService planService){
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

        for(Libro libros : librosComprados){
            ProductosCompra productos = new ProductosCompra(compra);
            productos.setLibro(libros);
            repositorioProductosCompra.crearProducto(productos);
            usuario.setLibrosComprados(libros);
            repositorioUsuario.modificar(usuario);
        }

        if (usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("standard")){
            cuponCadaDosCompras(usuario);
            repositorioUsuario.modificar(usuario);
        }

    }

    @Override
    public void cuponCadaDosCompras(Usuario usuario) {
        Integer totalCompras = repositorioUsuario.cantidadDeCompras(usuario);
        Integer cuponesNuevos = totalCompras / 2; //1
        Integer cuponesActuales = usuario.getCuponesDeDescuento().size() - 1; //0
        Integer cuponesAAgregar = 0;

        if (cuponesActuales < 0){
            cuponesAAgregar = cuponesNuevos - usuario.getCuponesDeDescuento().size();
        } else {
            cuponesAAgregar = cuponesNuevos - cuponesActuales;
        }

        for (int i = 0; i < cuponesAAgregar; i++) {
            planService.beneficioCuponPlan(usuario, 30);
        }
    }
}
