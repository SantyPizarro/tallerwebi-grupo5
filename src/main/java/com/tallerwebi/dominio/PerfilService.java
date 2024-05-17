package com.tallerwebi.dominio;

public interface PerfilService {

    void editarDescripcionPerfil(String descripcion,Usuario usuario);
    Usuario buscarUsuario(String email,String password);
    void editarUsuario(Usuario usuario,Usuario usuario2);
}
