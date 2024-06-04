package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioMensajeContacto {

    private final RepositorioMensajeContacto repositorioMensajeContacto;

    @Autowired
    public ServicioMensajeContacto(RepositorioMensajeContacto repositorioMensajeContacto) {
        this.repositorioMensajeContacto = repositorioMensajeContacto;
    }

    public List<MensajeContacto> obtenerMensajes(){
        return repositorioMensajeContacto.obtenerMensajes();
    }

    public void agregarMensaje(String nombre, String email, String telefono, String mensaje){
        repositorioMensajeContacto.agregarMensaje(nombre, email, telefono, mensaje);

    }




}
