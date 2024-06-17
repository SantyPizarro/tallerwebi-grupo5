package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class PlanRepositoryImpl implements PlanRepository {

    final private SessionFactory sessionFactory;

    @Autowired
    public PlanRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarPlan(Plan plan) {
        sessionFactory.getCurrentSession().save(plan);
    }

    @Override
    public Plan buscarPlan(Long id) {
        return sessionFactory.getCurrentSession().get(Plan.class, id);

    }

    @Override
    public Usuario verificarPlanDeUsuario(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("id", usuario.getId()))
                .add(Restrictions.isNotNull("plan"))
                .uniqueResult();
    }

}
