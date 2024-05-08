package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Libro {
    @Id
    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private String fechaPublicacion;
}
