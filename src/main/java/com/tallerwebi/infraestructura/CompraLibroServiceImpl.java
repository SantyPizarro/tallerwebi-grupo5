package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CompraLibroService;
import com.tallerwebi.dominio.Libro;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompraLibroServiceImpl implements CompraLibroService {

    private Double subtotal = 0.0;



    @Override
    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("El principito", 7.99, "Edelvives"));
        libros.add(new Libro("La sombra", 5.99, "Edelvives"));
        libros.add(new Libro("Martin Fierro", 5.99, "Edelvives"));
        return libros;
    }

    /*@Override
    public Double sumarSubtotal(String titulo) {
        List<Libro> libros = obtenerLibros();
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                subtotal += 6.0;
                break;
            }
        }
        return subtotal;
    }*/

    @Override
    public Double sumarNumeros(Integer numero1, Integer numero2) {
        subtotal+= numero1 + numero2;
        return subtotal;
    }

    @Override
    public Double sumarSubtotal(String titulo) {
        List<Libro> libros = obtenerLibros();
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                subtotal += libro.getPrecio();
                break;
            }
        }
        return subtotal;
    }
}
