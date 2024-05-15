package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicioLibro {

    private final RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibro(RepositorioLibro repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    public List<Libro> obtenerTodosLosLibros(){
        return repositorioLibro.obtenerTodosLosLibros();
    }

    public List<Libro> obtenerEditoriales(){
        return repositorioLibro.obtenerEditoriales();
    }

    public List<Libro> filtrarPorEditorial(String editorial){
        return repositorioLibro.filtrarPorEditoral(editorial);
    }

    public List<Libro> filtrarPorPrecio(double precioMinimo, double precioMaximo){
        return repositorioLibro.filtrarPorPrecio(precioMinimo, precioMaximo);
    }
}