package com.tallerwebi.dominio;


import java.util.Collection;

public interface NotificacionService {

    void aceptarNotificacion(String tipoNotificacion, Long idNotificacion);

    Integer cantidadDeNotificaciones(Usuario usuario);
}
