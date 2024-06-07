package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

public interface PerfilService {



    Usuario buscarUsuario(String email,String password);
    void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto);
    Usuario buscarUsuarioPorId(Long id);
    void addLibroFavorito(Usuario usuario, Libro libro);
    void eliminarLibroFavorito(Usuario usuario, Libro libro);
    void addLibroDeseado(Usuario usuario, Libro libro);
    void eliminarLibroDeseado(Usuario usuario, Libro libro);




}
