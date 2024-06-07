package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarritoService {

    void agregarLibrosAlCarrito(Long id, Carrito carrito) throws LibroNoAgregado;

    List <Libro> obtenerLibrosComprados(Carrito carrito);

    Double obtenerSubtotal(Carrito carrito);

}
