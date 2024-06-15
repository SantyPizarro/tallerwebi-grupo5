package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioIntercambio {
    void guardar(OfertaIntercambio oferta);

    OfertaIntercambio buscarOfertaEntreDosPersonas(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar);

    List<OfertaIntercambio> buscarOfertas(Usuario usuario);
}
