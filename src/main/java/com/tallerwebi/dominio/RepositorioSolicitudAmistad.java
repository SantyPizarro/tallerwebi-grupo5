package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioSolicitudAmistad {

    void guardar(SolicitudAmistad solicitud);

    List<SolicitudAmistad> buscarSolicitudes(Usuario usuario);
}
