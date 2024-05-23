package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

public interface PerfilService {

    //void editarDescripcionPerfil(String descripcion,Usuario usuario);
    Usuario buscarUsuario(String email,String password);
    //void editarUsuario(Usuario usuario,Usuario usuario2);
    void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto);
    Usuario buscarUsuarioPorId(Long id);
}
