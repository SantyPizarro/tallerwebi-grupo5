package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PerfilService;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public PerfilServiceImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void editarDescripcionPerfil(String descripcion, Usuario usuario) {
        usuario.setDescripcion(descripcion);
        repositorioUsuario.modificar(usuario);
    }

    public Usuario buscarUsuario(String email) {
        return repositorioUsuario.buscarUsuario(email);
    }

    @Override
    public void editarUsuario(Usuario usuario, Usuario usuario2) {
        usuario2.setDescripcion(usuario.getDescripcion());
        usuario2.setNombreDeUsuario(usuario.getNombreDeUsuario());
        usuario2.setGeneroFav1(usuario.getGeneroFav1());
        usuario2.setGeneroFav2(usuario.getGeneroFav2());

        repositorioUsuario.modificar(usuario2);
    }
}
