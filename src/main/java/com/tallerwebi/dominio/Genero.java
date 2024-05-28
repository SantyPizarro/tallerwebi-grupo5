package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Genero {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "genero")
    private List<Libro> libros;



    public Genero(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public Genero() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
