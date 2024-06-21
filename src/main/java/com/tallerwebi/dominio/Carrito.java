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
        this.total = 0.0;
    }

    public void agregarLibroAlCarrito(Libro libro) {
        // Verificar si el libro ya está en el carrito
        if (libros.contains(libro)) {
            return; // No agregar el libro ni actualizar los totales si ya está en el carrito
        }
        libros.add(libro);
        subtotal += libro.getPrecio();
        total = subtotal;  // Inicialmente el total es igual al subtotal
    }

    public void aplicarDescuento(Double descuento) {
        if (descuento < 0 || descuento > subtotal) {
            throw new IllegalArgumentException("Descuento inválido");
        }
        this.total = this.subtotal - descuento;
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

    public void eliminarLibro(Libro libro) {
        if (libros.remove(libro)) {
            subtotal -= libro.getPrecio();
            total = subtotal;  // Resetear el total al subtotal
        }
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
