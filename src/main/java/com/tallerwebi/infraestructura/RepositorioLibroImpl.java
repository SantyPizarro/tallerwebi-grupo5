package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioLibroImpl implements RepositorioLibro {


    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioLibroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Libro WHERE LOWER(titulo) LIKE LOWER(:titulo)";
        Query query = session.createQuery(hql);
        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public Libro buscarUnLibroPorSuTitulo(String titulo){
        return (Libro) sessionFactory.getCurrentSession().createCriteria(Libro.class)
                .add(Restrictions.eq("titulo", titulo))
                .uniqueResult();
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT DISTINCT l FROM Libro l", Libro.class).getResultList();
    }

    @Override
    public List<Libro> obtenerEditoriales(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT DISTINCT libro.editorial FROM Libro libro", Libro.class).getResultList();
    }

    @Override
    public List<Libro> filtrarPorEditoral(String editorial) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Libro WHERE editorial = :editorial";
        Query query = session.createQuery(hql);
        query.setParameter("editorial", editorial);
        return query.getResultList();

        }

    @Override
    public List<Libro> filtrarPorPrecio(double precioMinimo, double precioMaximo) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Libro WHERE precio BETWEEN :min AND :max";
        Query query = session.createQuery(hql);
        query.setParameter("min", precioMinimo);
        query.setParameter("max", precioMaximo);
        return query.getResultList();

    }

}