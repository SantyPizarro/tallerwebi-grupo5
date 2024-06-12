package com.tallerwebi.dominio;

public interface IntercambioService {
    void enviarOfertaIntercambio(Usuario usuario, Libro libroADar, Usuario amigo, Libro libroPedido);

    void aceptarOferta(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar);
}
