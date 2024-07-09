package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSliderHero;
import com.tallerwebi.dominio.SliderHero;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RepositorioSliderHeroImpl implements RepositorioSliderHero {

    private final SessionFactory sessionFactory;

    public RepositorioSliderHeroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<SliderHero> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM SliderHero", SliderHero.class).list();
    }

    @Override
    @Transactional
    public void save(SliderHero sliderHero) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sliderHero);
    }

    @Override
    @Transactional
    public void delete(SliderHero sliderHero) {
        Session session = sessionFactory.getCurrentSession();
        SliderHero SliderHero = session.createQuery("from SliderHero where imageName = :imageName", SliderHero.class)
                .setParameter("imageName", sliderHero.getImageName())
                .uniqueResult();
        if (SliderHero != null) {
            session.delete(SliderHero);
        }
    }
}