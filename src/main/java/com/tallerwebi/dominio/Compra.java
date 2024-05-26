package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    private LocalDate fechaDeCompra;

    public Compra(Usuario usuario) {
        this.usuario = usuario;
        this.fechaDeCompra = LocalDate.now();
    }

    public Compra() {

    }
}