package com.tallerwebi.dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Preferencias {

    private Map<Genero, Integer> puntuacionesGeneros;

    public Preferencias(Usuario usuario) {
        this.puntuacionesGeneros = new HashMap<>();

        contarGeneros(usuario.getLibrosFavoritos(), 3);
        contarGeneros(usuario.getLibrosComprados(), 2);
        contarGeneros(usuario.getLibrosDeseados(), 1);
    }

    private void contarGeneros(Set<Libro> libros, int peso) {
        for (Libro libro : libros) {
            Genero genero = libro.getGenero();
            puntuacionesGeneros.put(genero, puntuacionesGeneros.getOrDefault(genero, 0) + peso);
        }
    }

    public List<Genero> getTopGeneros(int topN) {
        return puntuacionesGeneros.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Método para obtener el puntaje de un género específico
    public int getPuntajeGenero(Genero genero) {
        return puntuacionesGeneros.getOrDefault(genero, 0);
    }
}