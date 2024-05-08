package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioBarraBusqueda;
import com.tallerwebi.dominio.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioBarraBusquedaImpl implements ServicioBarraBusqueda {

    @Autowired
    private RepositorioLibro repositorioLibro;

    @Override
    public List<Libro> buscarPorTituloOAutor(String titulo, String autor) {
        return repositorioLibro.buscarPorTituloOAutor(titulo, autor);
    }
}