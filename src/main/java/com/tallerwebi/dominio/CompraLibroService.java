package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;


public interface CompraLibroService {
    void registrarCompra(Usuario usuario, Carrito carrito);

    void cuponCadaDosCompras(Usuario usuario);
}
