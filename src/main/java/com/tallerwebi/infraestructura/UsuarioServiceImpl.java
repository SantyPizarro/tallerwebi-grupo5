package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public UsuarioServiceImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario= repositorioUsuario;
    }


    @Override
    public List<Usuario> listar(Usuario usuario) {
        return repositorioUsuario.buscarTodos(usuario);
    }

    @Override
    public List<Usuario> mostrarUsers() {
        return repositorioUsuario.buscarUsers();
    }

    @Override
    public List<Usuario> mostrarAdmins() {
        return repositorioUsuario.buscarAdmins();
    }
}
