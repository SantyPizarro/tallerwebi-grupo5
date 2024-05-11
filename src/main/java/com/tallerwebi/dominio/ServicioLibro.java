package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicioLibro {

    private final RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibro(RepositorioLibro repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    public List<Libro> obtenerTodosLosLibros(){
        return repositorioLibro.obtenerTodosLosLibros();
    }
}