package com.tallerwebi.dominio;

import java.util.List;

public interface IntercambioService {
    void enviarOfertaIntercambio(Usuario usuario, Libro libroADar, Usuario amigo, Libro libroPedido);

    List<OfertaIntercambio> buscarOfertas(Usuario usuario);
}
