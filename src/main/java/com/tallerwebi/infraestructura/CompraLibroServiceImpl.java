package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private RepositorioCompra repositorioCompra;
    private RepositorioProductosCompra repositorioProductosCompra;

    @Autowired
    public CompraLibroServiceImpl (RepositorioCompra repositorioCompra, RepositorioProductosCompra repositorioProductosCompra){
        this.repositorioCompra = repositorioCompra;
        this.repositorioProductosCompra = repositorioProductosCompra;
    }

    @Override
    public void registrarCompra(Usuario usuario, Carrito carrito) {
        Compra compra = new Compra(usuario);
        repositorioCompra.crearCompra(compra);
        List <Libro> librosComprados = carrito.getLibros();

        for(Libro libros : librosComprados){
            ProductosCompra productos = new ProductosCompra(compra);
            productos.setLibro(libros);
            repositorioProductosCompra.crearProducto(productos);

        }

    }
}
