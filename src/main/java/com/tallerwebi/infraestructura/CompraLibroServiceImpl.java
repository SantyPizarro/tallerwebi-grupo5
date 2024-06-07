package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private RepositorioCompra repositorioCompra;
    private RepositorioProductosCompra repositorioProductosCompra;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public CompraLibroServiceImpl (RepositorioCompra repositorioCompra, RepositorioProductosCompra repositorioProductosCompra,RepositorioUsuario repositorioUsuario){
        this.repositorioCompra = repositorioCompra;
        this.repositorioProductosCompra = repositorioProductosCompra;
        this.repositorioUsuario = repositorioUsuario;
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
        }

        repositorioUsuario.modificar(usuario);

    }
}
