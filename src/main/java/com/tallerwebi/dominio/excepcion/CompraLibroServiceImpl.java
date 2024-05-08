package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private Double subtotal = 8.0;



    @Override
    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("El principito", 5.99, "Edelvives"));
        libros.add(new Libro("La sombra", 5.99, "Edelvives"));
        libros.add(new Libro("Martin Fierro", 5.99, "Edelvives"));
        return libros;
    }

    @Override
    public Double sumarSubtotal(String titulo) {
        List<Libro> libros = obtenerLibros();
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                subtotal += 6.3;
                break;
            }
        }
        return subtotal;
    }

}
