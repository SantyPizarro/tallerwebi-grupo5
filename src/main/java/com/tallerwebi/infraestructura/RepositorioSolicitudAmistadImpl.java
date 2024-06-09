package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSolicitudAmistad;
import com.tallerwebi.dominio.SolicitudAmistad;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioSolicitudAmistadImpl implements RepositorioSolicitudAmistad {

    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudAmistadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(SolicitudAmistad solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }

    @Override
    public List<SolicitudAmistad> buscarSolicitudes(Usuario usuario) {
        return sessionFactory.getCurrentSession().createQuery("from SolicitudAmistad s where s.solicitado = :usuario", SolicitudAmistad.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
}
