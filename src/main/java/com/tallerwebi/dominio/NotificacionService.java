package com.tallerwebi.dominio;

public interface NotificacionService {

    void aceptarNotificacion(String tipoNotificacion, Long idNotificacion);

    void rechazarSolicitud(String tipoNotificacion, Long idNotificacion);

    Integer cantidadDeNotificaciones(Usuario usuario);
}
