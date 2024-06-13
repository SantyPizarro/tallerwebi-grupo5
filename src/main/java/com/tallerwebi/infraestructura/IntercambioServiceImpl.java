package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class IntercambioServiceImpl implements IntercambioService {

    private RepositorioIntercambio repositorioIntercambio;

    @Autowired
    public IntercambioServiceImpl(RepositorioIntercambio repositorioIntercambio) {
        this.repositorioIntercambio = repositorioIntercambio;
    }

    @Override
    public List<OfertaIntercambio> buscarOfertas(Usuario usuario) {
        return repositorioIntercambio.buscarOfertas(usuario);
    }

    @Override
    public void enviarOfertaIntercambio(Usuario usuario, Libro libroADar, Usuario amigo, Libro libroPedido) {
        OfertaIntercambio oferta = new OfertaIntercambio();
        oferta.setSolicitante(usuario);
        oferta.setLibroSolicitante(libroADar);
        oferta.setSolicitado(amigo);
        oferta.setLibroSolicitado(libroPedido);
        oferta.setFechaOferta(LocalDateTime.now());
        repositorioIntercambio.guardar(oferta);
    }

    @Override
    public void aceptarOferta(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar) {
        OfertaIntercambio oferta =  repositorioIntercambio.buscarOfertaEntreDosPersonas(aceptante,libroARecibir, solicitante,libroADar);

        aceptante.setLibrosComprados(libroADar);
        solicitante.setLibrosComprados(libroARecibir);

        aceptante.eliminarLibro(libroARecibir);
        solicitante.eliminarLibro(libroADar);
        repositorioIntercambio.guardar(oferta);
    }
}
