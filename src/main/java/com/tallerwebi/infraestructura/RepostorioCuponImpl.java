package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CodigoDescuento;
import com.tallerwebi.dominio.Cupon;
import com.tallerwebi.dominio.RepositorioCupon;
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
        sessionFactory.getCurrentSession().saveOrUpdate(codigoDescuento);
    }


}
