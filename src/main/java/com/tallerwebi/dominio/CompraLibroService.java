package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;


public interface CompraLibroService {

    List<Libro> obtenerLibros();
    Double sumarSubtotal(String titulo);
    Double sumarNumeros(Integer numero1, Integer numero2);
}
