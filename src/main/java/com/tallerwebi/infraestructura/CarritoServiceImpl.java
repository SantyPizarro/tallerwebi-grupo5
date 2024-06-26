package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service("CarritoService")
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private RepositorioLibro repositorioLibro;
    private RepositorioCupon repositorioCupon;

    @Autowired
    public CarritoServiceImpl(RepositorioLibro repositorioLibro, RepositorioCupon repositorioCupon) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioCupon = repositorioCupon;
    }

    @Override
    public void agregarLibrosAlCarrito(Long id, Carrito carrito) throws LibroNoAgregado {
        Libro libroEncontrado = buscarLibroPorId(id);
        if (libroEncontrado != null) {
            carrito.agregarLibroAlCarrito(libroEncontrado);
        } else {
            throw new LibroNoAgregado();
        }
    }

    @Override
    public Set<Libro> obtenerLibrosComprados(Carrito carrito) {
        return carrito.getLibros();
    }

    @Override
    public Double obtenerSubtotal(Carrito carrito) {
        return carrito.getSubtotal();
    }

    @Override
    public void eliminarLibroDeCarrito(Libro libro, Carrito carrito) {
        carrito.eliminarLibro(libro);
    }

    @Override
    public void setTotal(Carrito carrito, Double total) {
        carrito.setTotal(total);
    }

    @Override
    public Double obtenerTotal(Carrito carrito) {
        return carrito.getTotal();
    }

    @Override
    public Cupon buscarCuponPorId(Long id) {
        return repositorioCupon.buscarCuponPorId(id);
    }

    @Override
    public void eliminarCupon(Cupon cupon) {
        repositorioCupon.eliminarCupon(cupon);
    }

    @Override
    public void calcularTotalConCupon(Carrito carrito, Cupon cupon) {
        Double subtotal = obtenerSubtotal(carrito);
        Double descuento = (subtotal * cupon.getDescuento()) / 100;
        carrito.aplicarDescuento(descuento);
    }

    private Libro buscarLibroPorId(Long id) {
        return repositorioLibro.buscarLibroPorId(id);
    }
}
