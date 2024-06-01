package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

public interface PerfilService {



    Usuario buscarUsuario(String email,String password);
    void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto);
    Usuario buscarUsuarioPorId(Long id);
    void addLibroFavorito(Long usuarioId, Libro libro);



}
