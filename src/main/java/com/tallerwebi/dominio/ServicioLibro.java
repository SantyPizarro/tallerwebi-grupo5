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

    public List<String> obtenerEditoriales(){
        return repositorioLibro.obtenerEditoriales();
    }

    public List<Libro> ordenarPorFechaAgregado(String fechaAgregado){
        return repositorioLibro.ordenarPorFechaAgregado(fechaAgregado);
    }

    public Libro mostrarDetalleLibro(String titulo){
        return repositorioLibro.buscarUnLibroPorSuTitulo(titulo);
    }

    public List<Libro> filtrarLibros(String editorial, Double precioMinimo, Double precioMaximo, String genero){
        return repositorioLibro.filtrarLibros(editorial, precioMinimo,precioMaximo, genero);
    }

    public List<String> obtenerGeneros(){
        return repositorioLibro.obtenerGeneros();
    }
}