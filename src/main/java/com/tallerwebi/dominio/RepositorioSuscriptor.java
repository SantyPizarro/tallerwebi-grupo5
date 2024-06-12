package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioSuscriptor {
    public void agregarSuscriptor(String email, String nombre);
    List<Suscriptor> obtenerTodosLosSucriptores();
}
