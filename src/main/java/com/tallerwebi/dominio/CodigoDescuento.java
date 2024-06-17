package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CodigoDescuento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    List<Cupon> cuponesDescuento = new ArrayList<Cupon>();



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Cupon> getCuponesDescuento() {
        return cuponesDescuento;
    }

    public void setCuponesDescuento(List<Cupon> cuponesDescuento) {
        this.cuponesDescuento = cuponesDescuento;
    }
}
