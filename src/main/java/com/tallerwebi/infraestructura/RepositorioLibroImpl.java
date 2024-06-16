package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;
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
    public List<String> obtenerEditoriales(){
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT DISTINCT l.editorial FROM Libro l";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public List<Libro> filtrarLibros(String editorial, Double precioMinimo, Double precioMaximo, String genero) {
        Session session = sessionFactory.getCurrentSession();


        StringBuilder hql = new StringBuilder("FROM Libro l WHERE 1=1");


        if (editorial != null && !editorial.isEmpty()) {
            hql.append(" AND l.editorial = :editorial");
        }
        if (precioMinimo != null) {
            hql.append(" AND l.precio >= :precioMinimo");
        }
        if (precioMaximo != null) {
            hql.append(" AND l.precio <= :precioMaximo");
        }


        if (genero != null && !genero.isEmpty()) {
            hql.append(" AND l.genero.nombre = :genero");
        }


        Query query = session.createQuery(hql.toString());


        if (editorial != null && !editorial.isEmpty()) {
            query.setParameter("editorial", editorial);
        }
        if (precioMinimo != null) {
            query.setParameter("precioMinimo", precioMinimo);
        }
        if (precioMaximo != null) {
            query.setParameter("precioMaximo", precioMaximo);
        }
        if (genero != null && !genero.isEmpty()) {
            query.setParameter("genero", genero);
        }


        return query.getResultList();
    }

    @Override
    public List<Libro> ordenarPorFechaAgregado() {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Libro ORDER BY STR_TO_DATE(fechaAgregado, '%Y-%m-%d') DESC";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public List<String> obtenerGeneros() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT DISTINCT g.nombre FROM Genero g";
        Query query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Genero buscarUnGeneroPorId(Long id){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Genero WHERE id = :id";
        org.hibernate.query.Query<Genero> query = session.createQuery(hql, Genero.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Libro WHERE id = :id";
        org.hibernate.query.Query<Libro> query = session.createQuery(hql, Libro.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Override
    public void agregar(Libro libroAgregar) {
        sessionFactory.getCurrentSession().save(libroAgregar);
    }

    @Override
    public void eliminarLibro(Libro libroAborrar) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Libro WHERE id = : id";
        Query query = session.createQuery(hql);
        query.setParameter("id", libroAborrar.getId());
        query.executeUpdate();
    }

    @Override
    public List<Libro> obtenerListaDeGeneros(List<String> generos) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT l FROM Libro l WHERE l.genero.nombre IN :generos";
        return session.createQuery(hql, Libro.class)
                .setParameter("generos", generos)
                .getResultList();
    }


}