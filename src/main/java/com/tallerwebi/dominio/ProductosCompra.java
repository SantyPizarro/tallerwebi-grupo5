package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ProductosCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compra compra;

    @OneToOne
    private Libro libro;

    private Double precioProducto;

    public ProductosCompra(Compra compra){
        this.compra = compra;
    }

    public ProductosCompra() {}

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        this.precioProducto = libro.getPrecio();
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
