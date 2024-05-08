package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.Libro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraLibroService {

    List<Libro> obtenerLibros();
    Double sumarSubtotal(String titulo);

}
