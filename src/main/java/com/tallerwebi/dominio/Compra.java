package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    private List<ProductosCompra> productosCompra;

    private LocalDate fechaDeCompra;

    public Compra(Usuario usuario) {
        this.usuario = usuario;
        this.fechaDeCompra = LocalDate.now();
        this.productosCompra = new ArrayList<>();
    }

    public Compra() {

    }

    public Double getPrecioTotal() {
        return productosCompra.stream()
                .mapToDouble(ProductosCompra::getPrecioProducto)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaDeCompra() {
        return fechaDeCompra;
    }


    public List<ProductosCompra> getProductosCompra() {
        return productosCompra;
    }


}
