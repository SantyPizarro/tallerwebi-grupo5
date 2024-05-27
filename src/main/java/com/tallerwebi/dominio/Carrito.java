package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class Carrito {

    private Long id;
    private List<Libro> libros;
    private Usuario usuario;

    public Carrito() {
        this.libros = new ArrayList<>();
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

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void limpiar() {
        this.libros.clear();
    }
}
