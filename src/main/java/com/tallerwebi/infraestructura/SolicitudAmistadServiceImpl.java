package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SolicitudAmistadServiceImpl implements SolicitudAmistadService {

    private RepositorioSolicitudAmistad repositorioSolicitudAmistad;

    @Autowired
    public SolicitudAmistadServiceImpl (RepositorioSolicitudAmistad repositorioSolicitudAmistad){
        this.repositorioSolicitudAmistad = repositorioSolicitudAmistad;
    }

    @Override
    public void enviarSolicitud(Usuario solicitante, Usuario solicitado) {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setSolicitante(solicitante);
        solicitud.setSolicitado(solicitado);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        repositorioSolicitudAmistad.guardar(solicitud);
    }

    @Override
    public List<SolicitudAmistad> buscarSolicitudes(Usuario usuario) {
        return repositorioSolicitudAmistad.buscarSolicitudes(usuario);
    }

    @Override
    public Boolean comprobarSolicitudPendiente(Usuario solicitante, Usuario solicitado) {
        SolicitudAmistad solicitud =  repositorioSolicitudAmistad.buscarSolicitudAmistadEntreDosPersonas(solicitante, solicitado);
        if(solicitud!=null){
            return true;
        }
        return false;
    }

}
