package com.tallerwebi.dominio;

import java.util.List;

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
}

