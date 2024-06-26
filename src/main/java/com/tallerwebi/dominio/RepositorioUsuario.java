package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    Usuario buscarPorId(Long id);
    Usuario buscarUsuarioPassword(String email, String password);
    List<Usuario> buscarTodos(Usuario usuario);
    Usuario buscarPorToken(String token);

    List<Usuario> buscarUsers();

    List<Usuario> buscarAdmins();

    List<Compra> historialDeCompras(Usuario usuario);

    Set<Usuario> buscarAmigos(Usuario usuario);

    void eliminarUsuario(Usuario usuario);

    Set<Libro> buscarMisLibros(Usuario usuario);

    Integer cantidadDeCompras(Usuario usuario, LocalDateTime fechaCompraPlan);

    List<Usuario> buscarTodosLosUsuarios();

    void eliminarRelacionesDeAmistad(Usuario usuario);
}

