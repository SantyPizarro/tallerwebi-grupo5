package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public Libro buscarLibro(String titulo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Libro WHERE titulo = :titulo", Libro.class)
                .setParameter("titulo", titulo)
                .uniqueResult();
    }

    @Override
    public List<Libro> buscarPorTituloOAutor(String titulo, String autor) {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM Libro WHERE 1=1";
        if (titulo != null && !titulo.isEmpty()) {
            query += " AND titulo LIKE :titulo";
        }
        if (autor != null && !autor.isEmpty()) {
            query += " AND autor LIKE :autor";
        }
        return session.createQuery(query, Libro.class)
                .setParameter("titulo", "%" + titulo + "%")
                .setParameter("autor", "%" + autor + "%")
                .getResultList();
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