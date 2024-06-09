package com.tallerwebi.dominio;

import java.util.List;

public interface SolicitudAmistadService {
void enviarSolicitud(Usuario solicitante, Usuario solicitado);

    List <SolicitudAmistad> buscarSolicitudes(Usuario usuario);
}
