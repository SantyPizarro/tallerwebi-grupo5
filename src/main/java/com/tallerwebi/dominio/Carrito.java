package com.tallerwebi.dominio;


import java.util.HashSet;
import java.util.Set;


public class Carrito {

    private Long id;
    private Set<Libro> libros;
    private Usuario usuario;
    private Double subtotal;
    private Double total;

    public Carrito() {
        this.libros = new HashSet<>();
        this.subtotal = 0.0;
        this.total = subtotal;
    }

    public void agregarLibroAlCarrito(Libro libro) {
        libros.add(libro);
        total += libro.getPrecio();
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
        this.subtotal = 0.0;
        this.total = 0.0;
    }

    public void eliminarLibro(Libro libro, Carrito carrito) {
        carrito.getLibros().remove(libro);
        carrito.setTotal(carrito.getTotal() - libro.getPrecio());
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
