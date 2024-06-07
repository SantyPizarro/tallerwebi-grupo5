package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Carrito {

    private Long id;
    private Set<Libro> libros;
    private Usuario usuario;

    public Carrito() {
        this.libros = new HashSet<>();
    }

    public void agregarLibroAlCarrito(Libro libro) {
        libros.add(libro);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    public void limpiar() {
        this.libros.clear();
    }
}
