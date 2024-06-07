package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.MensajeContacto;
import com.tallerwebi.dominio.RepositorioMensajeContacto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioMensajeContactoImpl implements RepositorioMensajeContacto {

    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMensajeContactoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MensajeContacto> obtenerMensajes() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM MensajeContacto";
        Query<MensajeContacto> query = session.createQuery(hql, MensajeContacto.class);
        return query.getResultList();

    }

    @Override
    public void agregarMensaje(String nombre, String email, String telefono, String mensaje) {
        Session session = sessionFactory.getCurrentSession();
        MensajeContacto mensajeContacto = new MensajeContacto();
        mensajeContacto.setNombre(nombre);
        mensajeContacto.setEmail(email);
        mensajeContacto.setTelefono(telefono);
        mensajeContacto.setMensaje(mensaje);
        session.save(mensajeContacto);
    }

}
