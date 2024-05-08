package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Libro {
    @Id

    private String titulo;
    private String autor;
    private String editorial;
    private String fechaPublicacion;
    private Double precio;

    public Libro(String titulo, double precio, String editorial) {
        this.titulo = titulo;
        this.precio = precio;
        this.editorial = editorial;
    }


    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
