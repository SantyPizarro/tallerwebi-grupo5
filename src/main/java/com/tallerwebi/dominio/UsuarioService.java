package com.tallerwebi.dominio;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar(Usuario usuario);

    List<Usuario> mostrarUsers();

    List<Usuario> mostrarAdmins();

    Usuario buscarPorId(Long idAmigo);
}
