package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.OfertaIntercambio;
import com.tallerwebi.dominio.Plan;
import com.tallerwebi.dominio.PlanRepository;
import org.hibernate.SessionFactory;
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
}
