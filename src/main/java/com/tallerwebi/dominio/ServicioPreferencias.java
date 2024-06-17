package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPreferencias {

    @Autowired
    private RepositorioLibro repositorioLibro;

    @Autowired
    private RepositorioPreferencias repositorioPreferencias;

    public ServicioPreferencias(RepositorioPreferencias repositorioPreferencias) {
        this.repositorioPreferencias = repositorioPreferencias;
    }

    public List<Libro> obtenerListaDeGeneros(List<String> generos) {
        return repositorioLibro.obtenerListaDeGeneros(generos);
    }

    public List<Libro> recomendarLibros(Preferencias preferencias){
        return repositorioPreferencias.recomendarLibros(preferencias);
    }
}
