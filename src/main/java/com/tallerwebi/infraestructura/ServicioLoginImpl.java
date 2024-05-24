package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email) {
        return repositorioUsuario.buscarUsuario(email);
    }

    @Override
    public void registrar(DatosRegistro datosRegistro) throws UsuarioExistente, NoCoincideContrasenia {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(datosRegistro.getEmail());
        Usuario usuario = new Usuario();
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        } else {
            if (datosRegistro.getPassword().equals(datosRegistro.getContraseniaDuplicada())) {
                usuario.setEmail(datosRegistro.getEmail());
                usuario.setPassword(datosRegistro.getPassword());
                usuario.setNombre(datosRegistro.getNombre());
                usuario.setApellido(datosRegistro.getApellido());
                usuario.setNombreDeUsuario(datosRegistro.getNombre_usuario());
                usuario.setDescripcion("Me llamo " + datosRegistro.getNombre() + " y he sido un ávido lector desde que era niño. Mis géneros favoritos son ficcion y deporte");
                usuario.setGeneroFav1("Ficcion");
                usuario.setGeneroFav2("Deporte");
                repositorioUsuario.guardar(usuario);
            } else {
                throw new NoCoincideContrasenia();
            }
        }
    }

}

