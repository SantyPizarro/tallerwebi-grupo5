package com.tallerwebi.dominio;

import java.util.List;


public interface RepositorioMensajeContacto {
    public List<MensajeContacto> obtenerMensajes();
    public void agregarMensaje(String nombre, String email, String telefono, String mensaje);
}
