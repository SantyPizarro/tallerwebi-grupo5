package com.tallerwebi.dominio;

public class DatosRegistro {

    private String nombre;
    private String apellido;
    private String nombre_usuario;
    private String email;
    private String password;
    private String contraseniaDuplicada;

    public DatosRegistro() {

    }

    public DatosRegistro(String nombre, String apellido, String nombre_usuario, String email, String password, String contraseniaDuplicada) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.password = password;
        this.contraseniaDuplicada = contraseniaDuplicada;
    }

    public String getContraseniaDuplicada() {
        return contraseniaDuplicada;
    }

    public void setContraseniaDuplicada(String contraseniaDuplicada) {
        this.contraseniaDuplicada = contraseniaDuplicada;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
