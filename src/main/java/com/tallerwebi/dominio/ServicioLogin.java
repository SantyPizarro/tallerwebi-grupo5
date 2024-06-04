package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.TokenInvalido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email);
    void registrar(DatosRegistro datosRegistro) throws UsuarioExistente, NoCoincideContrasenia;
    void enviarTokenDeVerificacion(Usuario usuario);
    void validarToken(String token) throws TokenInvalido;
}
