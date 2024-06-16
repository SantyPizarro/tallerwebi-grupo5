package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSuscriptor;
import com.tallerwebi.dominio.ServicioSuscriptor;
import com.tallerwebi.dominio.Suscriptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public class RepositorioSuscriptorImpl implements RepositorioSuscriptor {

    private final SessionFactory sessionFactory;
    private ServicioSuscriptor servicioSuscriptor;

    @Autowired
    public RepositorioSuscriptorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void agregarSuscriptor(String email, String nombre) {
        Suscriptor suscriptor = new Suscriptor(email, nombre);
        sessionFactory.getCurrentSession().save(suscriptor);
    }

    @Override
    @Transactional
    public List<Suscriptor> obtenerTodosLosSucriptores() {
        return sessionFactory.getCurrentSession().createQuery("FROM Suscriptor", Suscriptor.class).list();
    }

}
