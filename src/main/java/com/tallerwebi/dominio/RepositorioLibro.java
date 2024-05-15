package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLibro {

    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> obtenerTodosLosLibros();
    List<Libro> obtenerEditoriales();
    List<Libro> filtrarPorEditoral(String editorial);
    List<Libro> filtrarPorPrecio(double precioMinimo, double precioMaximo);

}
