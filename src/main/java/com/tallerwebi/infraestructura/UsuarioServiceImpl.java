package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public UsuarioServiceImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario= repositorioUsuario;
    }


    @Override
    public void actualizarUser(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public Set<Usuario> listar(Usuario usuario) {
        List<Usuario> usuarios = repositorioUsuario.buscarTodos(usuario);
        Set<Usuario> usuariosAux = new HashSet<>();

        // Crear un set con los amigos del usuario para búsqueda rápida
        Set<Usuario> amigosSet = repositorioUsuario.buscarAmigos(usuario);

        for (Usuario usuarioAux : usuarios) {
            if (usuarioAux != null && !amigosSet.contains(usuarioAux)) {
                usuariosAux.add(usuarioAux);
            }
        }

        return usuariosAux;

    }

    @Override
    public List<Usuario> mostrarUsers() {
        return repositorioUsuario.buscarUsers();
    }

    @Override
    public List<Usuario> mostrarAdmins() {
        return repositorioUsuario.buscarAdmins();
    }

    @Override
    public Usuario buscarPorId(Long idAmigo) {
        return repositorioUsuario.buscarPorId(idAmigo);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        repositorioUsuario.eliminarUsuario(usuario);
    }


}
