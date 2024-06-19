package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoPlan;
import com.tallerwebi.dominio.TipoPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RepositorioTipoPlanImpl implements RepositorioTipoPlan {

    private final SessionFactory sessionFactory;

    public RepositorioTipoPlanImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String obtenerDescripcionPlanes(Long id) {
        Session session = sessionFactory.getCurrentSession();
        TipoPlan tipoPlan = session.get(TipoPlan.class, id);
        return tipoPlan != null ? tipoPlan.getDescripcion() : null;
    }
}
