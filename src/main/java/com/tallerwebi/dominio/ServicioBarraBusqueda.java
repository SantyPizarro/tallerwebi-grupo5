package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioBarraBusqueda{



    public ServicioBarraBusqueda(RepositorioLibro repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    private final RepositorioLibro repositorioLibro;

    public List<Libro> buscarPorTitulo(String titulo) {
        return repositorioLibro.buscarPorTitulo(titulo);
    }

}