package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Compra;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("repositorioUsuario")
@Transactional
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }


    @Override
    public Usuario buscarPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Usuario WHERE id = :id";
        Query query = session.createQuery(hql, Usuario.class);
        query.setParameter("id", id);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null si no se encuentra ningún resultado
        }
    }

    @Override
    public Usuario buscarUsuarioPassword(String email, String password) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public List<Usuario> buscarTodos(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE rol = :userRole AND id != :userId")
                .setParameter("userRole", "USER")
                .setParameter("userId", usuario.getId())
                .list();
    }

    @Override
    public Usuario buscarPorToken(String token) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("tokenDeVerificacion", token))
                .uniqueResult();
    }

    @Override
    public List<Usuario> buscarUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE rol = :userRole")
                .setParameter("userRole", "USER")
                .list();
    }

    @Override
    public List<Usuario> buscarAdmins() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Usuario WHERE rol = :userRole")
                .setParameter("userRole", "ADMIN")
                .list();
    }

    @Override
    public List<Compra> historialDeCompras(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Compra> criteriaQuery = builder.createQuery(Compra.class);

        Root<Compra> compraRoot = criteriaQuery.from(Compra.class);
        Join<Compra, Usuario> usuarioJoin = compraRoot.join("usuario");

        criteriaQuery.select(compraRoot);
        criteriaQuery.where(builder.equal(usuarioJoin.get("id"), usuario.getId()));

        Query<Compra> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Set<Usuario> buscarAmigos(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        List<Usuario> amigosUsuario =  session.createQuery(
                        "SELECT a.usuario FROM Amistad a WHERE a.amigo = :usuario", Usuario.class)
                .setParameter("usuario", usuario)
                .getResultList();

        return new HashSet<>(amigosUsuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Usuario WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", usuario.getId());
        query.executeUpdate();
    }


}
