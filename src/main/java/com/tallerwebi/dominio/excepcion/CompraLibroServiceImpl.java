package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Libro;

import java.util.ArrayList;
import java.util.List;

public class CompraLibroServiceImpl implements CompraLibroService {


    @Override
    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("El principito", 5.99, "Edelvives"));
        libros.add(new Libro("La sombra", 5.99, "Edelvives"));
        libros.add(new Libro("Martin Fierro", 5.99, "Edelvives"));
        return libros;
    }
}
