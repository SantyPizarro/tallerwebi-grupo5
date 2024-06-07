package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.TokenInvalido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private ServicioCorreo servicioCorreo;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, ServicioCorreo servicioCorreo){

        this.repositorioUsuario = repositorioUsuario;
        this.servicioCorreo = servicioCorreo;
    }

    @Override
    public Usuario consultarUsuario (String email) {
        return repositorioUsuario.buscarUsuario(email);
    }

    @Override
    public void registrar(DatosRegistro datosRegistro) throws UsuarioExistente, NoCoincideContrasenia {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(datosRegistro.getEmail());
        Usuario usuario = new Usuario();
        String token = UUID.randomUUID().toString();

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
                usuario.setRol("user");
                usuario.setTokenDeVerificacion(token);
                repositorioUsuario.guardar(usuario);
                enviarTokenDeVerificacion(usuario);
            } else {
                throw new NoCoincideContrasenia();
            }
        }
    }

    public void validarToken(String token) throws TokenInvalido {
        Usuario usuario = repositorioUsuario.buscarPorToken(token);
        if (usuario == null) {
            throw new TokenInvalido();
        }
        usuario.setEmailVerificado(true);
        usuario.setTokenDeVerificacion(null);
        repositorioUsuario.guardar(usuario);
    }

    public void enviarTokenDeVerificacion(Usuario usuario) {
        // Configurar el correo electrónico
        String destinatario = usuario.getEmail();
        String asunto = "Verificación de correo electrónico";
        String cuerpo = "Su código de verificación es: " + usuario.getTokenDeVerificacion();

        // Enviar el correo electrónico
        servicioCorreo.enviarCorreo(destinatario, asunto, cuerpo);

    }

}

