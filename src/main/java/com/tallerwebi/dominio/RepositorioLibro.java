package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLibro {

    Libro buscarLibro(String titulo);
    List<Libro> buscarPorTituloOAutor(String titulo, String Autor);
    List<Libro> obtenerTodosLosLibros();
    List<Libro> obtenerEditoriales();
    List<Libro> filtrarPorEditoral(String editorial);

}
