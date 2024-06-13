package com.tallerwebi.dominio;

import java.util.List;

public interface IntercambioService {
    void enviarOfertaIntercambio(Usuario usuario, Libro libroADar, Usuario amigo, Libro libroPedido);

    void aceptarOferta(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar);

    List<OfertaIntercambio> buscarOfertas(Usuario usuario);
}
