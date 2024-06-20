package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CodigoDescuento;
import com.tallerwebi.dominio.Cupon;
import com.tallerwebi.dominio.Genero;
import com.tallerwebi.dominio.RepositorioCupon;
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
        sessionFactory.getCurrentSession().saveOrUpdate(codigoDescuento);
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
        Session session = sessionFactory.getCurrentSession();

        // Eliminar referencias en usuario_cupon
        String hqlDelete = "DELETE FROM CodigoDescuento WHERE CodigoDescuento.id = :cuponesDescuento_id";
        session.createQuery(hqlDelete)
                .setParameter("cuponesDescuento_id", cupon.getId())
                .executeUpdate();

        session.delete(cupon);
    }

}
