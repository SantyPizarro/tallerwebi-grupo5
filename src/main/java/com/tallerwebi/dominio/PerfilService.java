package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PerfilService {



    Usuario buscarUsuario(String email,String password);
    void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto);
    Usuario buscarUsuarioPorId(Long id);
    void addLibroFavorito(Usuario usuario, Libro libro) throws LibroExistente;
    void eliminarLibroFavorito(Usuario usuario, Libro libro);
    void addLibroDeseado(Usuario usuario, Libro libro) throws LibroExistente;
    void eliminarLibroDeseado(Usuario usuario, Libro libro);

    List<Compra> historialDeCompras(Usuario usuario);

    Set<Usuario> buscarAmigos(Usuario usuario);


    Set<Libro> buscarMisLibros(Usuario usuario);
    Boolean verificarPlan(Usuario usuario);

    Set<Libro> buscarLibrosDePlan(Usuario usuario);
}
