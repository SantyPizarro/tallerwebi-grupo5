package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepostorioCuponImpl implements RepositorioCupon {

    final private SessionFactory sessionFactory;

    public RepostorioCuponImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarCupon(Cupon cupon) {
        sessionFactory.getCurrentSession().save(cupon);
    }

    @Override
    public void modificarCodigoDescuento(CodigoDescuento codigoDescuento) {
        sessionFactory.getCurrentSession().update(codigoDescuento);
    }

    @Override
    public void guardarCodigoDescuento(CodigoDescuento codigoDescuento) {
        sessionFactory.getCurrentSession().save(codigoDescuento);
    }

    @Override
    public CodigoDescuento buscarCodigoDescuento(Long id) {
        return sessionFactory.getCurrentSession().get(CodigoDescuento.class, id);
    }

    @Override
    public Cupon buscarCuponPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Cupon WHERE id = :id";
        org.hibernate.query.Query<Cupon> query = session.createQuery(hql, Cupon.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    public void eliminarCupon(Cupon cupon) {
        sessionFactory.getCurrentSession().delete(cupon);
    }

}
