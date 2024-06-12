package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioIntercambioImpl implements RepositorioIntercambio {

    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioIntercambioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(OfertaIntercambio oferta) {
        sessionFactory.getCurrentSession().save(oferta);
    }

    @Override
    public OfertaIntercambio buscarOfertaEntreDosPersonas(Usuario aceptante, Libro libroARecibir, Usuario solicitante, Libro libroADar) {
        return sessionFactory.getCurrentSession().createQuery("from OfertaIntercambio o where o.solicitado = :aceptante and s.solicitante = :solicitante and o.libroSolicitante = :libroARecibir and o.libroSolicitado = :libroADar", OfertaIntercambio.class)
                    .setParameter("aceptante", aceptante)
                    .setParameter("solicitante", solicitante)
                    .setParameter("libroARecibir", libroARecibir)
                    .setParameter("libroADar", libroADar)
                    .uniqueResult();
    }
}
