package com.tallerwebi.dominio;

import java.util.List;

public interface SolicitudAmistadService {
void enviarSolicitud(Usuario solicitante, Usuario solicitado);

    List <SolicitudAmistad> buscarSolicitudes(Usuario usuario);

    Boolean comprobarSolicitudPendiente(Usuario solicitante, Usuario solicitado);

    void aceptarSolicitud(Usuario aceptante, Usuario solicitante);

    SolicitudAmistad buscarPorId(Long idSolicitud);

    void rechazarSolicitud(SolicitudAmistad solicitud);
}
