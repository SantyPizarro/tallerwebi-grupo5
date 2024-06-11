package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String descripcion;
    private String rol;
    private Boolean activo = false;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String generoFav1;
    private String generoFav2;
    private String foto;
    private String tokenDeVerificacion;
    private Boolean emailVerificado = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_libros_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "libroFavorito_id")
    )
    private Set<Libro> librosFavoritos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_libros_deseados",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "libroDeseado_id")
    )
    private Set<Libro> librosDeseados;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_libros_comprados",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "libroComprado_id")
    )

    private Set<Libro> librosComprados;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getGeneroFav1() {
        return generoFav1;
    }

    public void setGeneroFav1(String generoFav1) {
        this.generoFav1 = generoFav1;
    }

    public String getGeneroFav2() {
        return generoFav2;
    }

    public void setGeneroFav2(String generoFav2) {
        this.generoFav2 = generoFav2;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public String getTokenDeVerificacion() {
        return tokenDeVerificacion;
    }

    public void setTokenDeVerificacion(String tokenDeVerificacion) {
        this.tokenDeVerificacion = tokenDeVerificacion;
    }

    public Boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public Set<Libro> getLibrosFavoritos() {
        return librosFavoritos;
    }

    public void setLibrosFavoritos(Set<Libro> librosFavoritos) {
        this.librosFavoritos = librosFavoritos;
    }

    public Set<Libro> getLibrosDeseados() {
        return librosDeseados;
    }

    public void setLibrosDeseados(Set<Libro> librosDeseados) {
        this.librosDeseados = librosDeseados;
    }

    public Set<Libro> getLibrosComprados() {
        return librosComprados;
    }

    public void setLibrosComprados(Libro libro) {
        this.librosComprados.add(libro);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
