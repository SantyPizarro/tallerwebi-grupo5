package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioCompra;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioCompraImpl implements RepositorioCompra {

    @Override
    public void crearCompra(Usuario usuario) {

    }
}
