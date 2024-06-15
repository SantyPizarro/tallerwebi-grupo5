package com.tallerwebi.dominio;

import java.time.LocalDateTime;

public interface NotificacionService {
    String getTipo();
    Usuario getSolicitante();
    LocalDateTime getFecha();
}
