package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositorioPreferenciasImpl implements RepositorioPreferencias{

    @Autowired
    private RepositorioLibro repositorioLibro;

    public List<Libro> recomendarLibros(Preferencias preferencias) {
        List<Genero> topGeneros = preferencias.getTopGeneros(3);
        List<String> nombresGeneros = topGeneros.stream()
                .map(Genero::getNombre)
                .collect(Collectors.toList());
        return repositorioLibro.obtenerListaDeGeneros(nombresGeneros);
    }
}
