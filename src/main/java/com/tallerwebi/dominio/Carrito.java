package com.tallerwebi.dominio;


import java.util.HashSet;
import java.util.Set;


public class Carrito {

    private Long id;
    private Set<Libro> libros;
    private Usuario usuario;
    private Double total;

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

    public void eliminarLibro(Libro libro, Carrito carrito) {
        carrito.getLibros().remove(libro);
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
