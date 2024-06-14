package com.tallerwebi.dominio;

import java.time.LocalDateTime;

public interface TipoNotificacionService {
    String getTipo();
    Usuario getSolicitante();
    LocalDateTime getFecha();
}
