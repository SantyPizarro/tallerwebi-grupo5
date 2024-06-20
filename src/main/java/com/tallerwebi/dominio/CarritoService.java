package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CarritoService {

    void agregarLibrosAlCarrito(Long id, Carrito carrito) throws LibroNoAgregado;

    Set<Libro> obtenerLibrosComprados(Carrito carrito);

    Double obtenerSubtotal(Carrito carrito);

    void eliminarLibroDeCarrito(Libro libro, Carrito carrito);

    void setTotal(Carrito carrito, Double total);

    Double obtenerTotal(Carrito carrito);

    Cupon buscarCuponPorId(Long id);

    void eliminarCupon(Cupon cupon);

    void calcularTotalConCupon(Carrito carrito, Cupon cupon);
}
