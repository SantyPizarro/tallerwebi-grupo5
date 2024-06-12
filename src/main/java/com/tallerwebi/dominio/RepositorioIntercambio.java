package com.tallerwebi.dominio;

public interface RepositorioIntercambio {
    void guardar(OfertaIntercambio oferta);

    OfertaIntercambio buscarOfertaEntreDosPersonas(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar);
}
