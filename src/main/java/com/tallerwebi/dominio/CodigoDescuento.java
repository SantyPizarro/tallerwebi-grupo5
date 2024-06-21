package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.*;

@Entity
public class CodigoDescuento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoDescuento that = (CodigoDescuento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
