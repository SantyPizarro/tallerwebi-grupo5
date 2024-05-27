package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Compra;
import com.tallerwebi.dominio.ProductosCompra;
import com.tallerwebi.dominio.RepositorioProductosCompra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioProductosCompraImpl implements RepositorioProductosCompra {

    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductosCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearProducto(ProductosCompra producto) {
        sessionFactory.getCurrentSession().save(producto);
    }
}



