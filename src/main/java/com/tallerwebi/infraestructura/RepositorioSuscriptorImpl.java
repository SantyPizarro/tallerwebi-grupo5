package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSuscriptor;
import com.tallerwebi.dominio.ServicioSuscriptor;
import com.tallerwebi.dominio.Suscriptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RepositorioSuscriptorImpl implements RepositorioSuscriptor {

    private final SessionFactory sessionFactory;
    private ServicioSuscriptor servicioSuscriptor;

    @Autowired
    public RepositorioSuscriptorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void agregarSuscriptor(String email, String nombre) {
        Session session = sessionFactory.getCurrentSession();
        Suscriptor suscriptor = new Suscriptor(email, nombre);
        suscriptor.setEmail(email);
        suscriptor.setName(nombre);
        session.save(suscriptor);

    }

}
