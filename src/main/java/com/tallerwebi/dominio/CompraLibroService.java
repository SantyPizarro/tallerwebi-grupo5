package com.tallerwebi.dominio;

import java.time.LocalDateTime;

public interface CompraLibroService {
    void registrarCompra(Usuario usuario, Carrito carrito);

    void cuponCadaDosCompras(Usuario usuario, LocalDateTime fechaCompraPlan);
}
