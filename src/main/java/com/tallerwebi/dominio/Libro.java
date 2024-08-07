package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Libro{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private String editorial;
    private String fechaPublicacion;
    private Double precio;
    private String ruta;
    private String descripcion;
    private String fechaAgregado;

    @ManyToMany(mappedBy = "librosComprados")
    private Set<Usuario> usuariosCompradores = new HashSet<>();


    public Libro(){

    }

    public Libro(double precio){
        this.precio = precio;
    }

    public Libro(Long id, String titulo){
        this.id = id;
        this.titulo = titulo;
    }

    public Libro(Long id, String titulo, Genero genero){
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
    }

    public Libro(String titulo, Double precio, Genero genero){
        this.titulo = titulo;
        this.precio = precio;
        this.genero = genero;
    }

    public Libro(String titulo, Double precio, String editorial, Genero genero){
        this.titulo = titulo;
        this.precio = precio;
        this.editorial = editorial;
        this.genero = genero;
    }

    public Libro(String titulo, double precio, String editorial) {
        this.titulo = titulo;
        this.precio = precio;
        this.editorial = editorial;
    }

    public Libro(String titulo, String autor, String editorial, String fechaPublicacion, Double precio, String descripcion, String ruta, Genero genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ruta = ruta;
        this.genero = genero;
        this.fechaAgregado = LocalDate.now().toString();
    }

    public Libro(String titulo, String fechaAgregado){
        this.titulo = titulo;
        this.fechaAgregado = fechaAgregado;

    }

    public Libro(String titulo, String editorial, String fechaAgregado, Double precio, Genero genero){
        this.titulo = titulo;
        this.editorial = editorial;
        this.fechaAgregado = fechaAgregado;
        this.precio = precio;
        this.genero = genero;
    }

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    public Libro(String titulo, Genero genero) {
        this.titulo = titulo;
        this.genero = genero;
    }

    public Genero getGenero(){
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setFechaAgregado(String fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public String getFechaAgregado() {
        return fechaAgregado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRuta(){ return ruta;}

    public void setRuta(String ruta){ this.ruta = ruta; }

    public Set<Usuario> getUsuariosCompradores() {
        return usuariosCompradores;
    }

    public void setUsuariosCompradores(Set<Usuario> usuariosCompradores) {
        this.usuariosCompradores = usuariosCompradores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(id, libro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", precio=" + precio +
                ", ruta='" + ruta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaAgregado='" + fechaAgregado + '\'' +
                ", usuariosCompradores=" + usuariosCompradores +
                ", genero=" + genero +
                '}';
    }
}

