package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ProductosCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compra compra;

}
