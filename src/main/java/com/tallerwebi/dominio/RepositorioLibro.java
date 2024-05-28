package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLibro {

    List<Libro> buscarPorTitulo(String titulo);
    Libro buscarUnLibroPorSuTitulo(String titulo);
    List<Libro> obtenerTodosLosLibros();
    List<String> obtenerEditoriales();
    List<Libro> filtrarLibros(String editorial, Double precioMinimo, Double precioMaximo, String genero);
    List<String> obtenerGeneros();
    //List<Libro> ordenarPorFechaAgregado(String fechaAgregado);
    Libro buscarLibroPorId(Long id);
}
