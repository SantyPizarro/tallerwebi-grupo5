package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private RepositorioSolicitudAmistad repositorioSolicitudAmistad;
    private RepositorioIntercambio repositorioIntercambio;

    @Autowired
    public NotificacionServiceImpl (RepositorioSolicitudAmistad repositorioSolicitudAmistad, RepositorioIntercambio repositorioIntercambio){
        this.repositorioSolicitudAmistad = repositorioSolicitudAmistad;
        this.repositorioIntercambio = repositorioIntercambio;
    }

    @Override
    public void aceptarNotificacion(String tipoNotificacion, Long idNotificacion)  {

        if (tipoNotificacion != null && tipoNotificacion.equalsIgnoreCase("amistad") && idNotificacion != null) {

            SolicitudAmistad solicitud =  repositorioSolicitudAmistad.buscarSolicitudPorId(idNotificacion);

            Amistad amistad = new Amistad(solicitud.getSolicitado(), solicitud.getSolicitante());
            Amistad amistad2 = new Amistad(solicitud.getSolicitante(), solicitud.getSolicitado());

            repositorioSolicitudAmistad.crearAmistad(amistad);
            repositorioSolicitudAmistad.crearAmistad(amistad2);

            repositorioSolicitudAmistad.aceptarSolicitud(solicitud, amistad);

            repositorioSolicitudAmistad.eliminarSolicitud(solicitud);
        }

        if (tipoNotificacion != null && tipoNotificacion.equalsIgnoreCase("intercambio") && idNotificacion != null){

            OfertaIntercambio oferta =  repositorioIntercambio.buscarOfertaPorId(idNotificacion);

            Usuario aceptante = oferta.getSolicitado();
            Usuario solicitante = oferta.getSolicitante();

            aceptante.eliminarLibro(oferta.getLibroSolicitado());
            aceptante.setLibrosComprados(oferta.getLibroSolicitante());

            solicitante.eliminarLibro(oferta.getLibroSolicitante());
            solicitante.setLibrosComprados(oferta.getLibroSolicitado());


            repositorioIntercambio.aceptarOferta(oferta);

            repositorioIntercambio.eliminarOferta(oferta);
        }

    }
}
