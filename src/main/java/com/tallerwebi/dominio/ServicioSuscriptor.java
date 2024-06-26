package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioSuscriptor {

    private final RepositorioSuscriptor repositorioSuscriptor;

    @Autowired
    public ServicioSuscriptor(RepositorioSuscriptor repositorioSuscriptor) {
        this.repositorioSuscriptor = repositorioSuscriptor;
    }

    public void agregarSuscriptor(String email, String nombre){
        repositorioSuscriptor.agregarSuscriptor(email, nombre);
    }

    public List<Suscriptor> obtenerTodosLosSuscriptores(){
       return repositorioSuscriptor.obtenerTodosLosSucriptores();
    }
}
