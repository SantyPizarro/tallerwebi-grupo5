package com.tallerwebi.dominio;

import java.util.List;
import java.util.Set;

public interface UsuarioService {
    Set<Usuario> listar(Usuario usuario);

    List<Usuario> mostrarUsers();

    List<Usuario> mostrarAdmins();

    Usuario buscarPorId(Long idAmigo);

    void eliminarUsuario(Usuario usuario);

    void actualizarUsuario(Usuario solicitado);
}
