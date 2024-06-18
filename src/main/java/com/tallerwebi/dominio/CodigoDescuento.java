package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CodigoDescuento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    Set<Cupon> cuponesDescuento = new HashSet<>();



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Cupon> getCuponesDescuento() {
        return cuponesDescuento;
    }

    public void setCuponesDescuento(Set<Cupon> cuponesDescuento) {
        this.cuponesDescuento = cuponesDescuento;
    }
}
