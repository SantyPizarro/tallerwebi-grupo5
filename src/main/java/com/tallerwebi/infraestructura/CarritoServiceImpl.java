package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.CarritoService;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("CarritoService")
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private RepositorioLibro repositorioLibro;




    @Autowired
    public CarritoServiceImpl(RepositorioLibro repositorioLibro){
        this.repositorioLibro = repositorioLibro;

    }

    @Override
    public void agregarLibrosAlCarrito(Long id, Carrito carrito) throws LibroNoAgregado {

        Libro libroEncontrado = buscarLibroPorId(id);

        if(libroEncontrado != null){
            carrito.agregarLibroAlCarrito(libroEncontrado);
        }else {
            throw  new LibroNoAgregado();
        }

    }

    @Override
    public List<Libro> obtenerLibrosComprados(Carrito carrito) {
        return carrito.getLibros();
    }

    @Override
    public Double obtenerSubtotal(Carrito carrito) {
        Double subtotal = 0.0;
        List<Libro> libros = obtenerLibrosComprados(carrito);
        for (Libro precioLibro : libros) {
            subtotal += precioLibro.getPrecio();
        }

        return subtotal;
    }

    private Libro buscarLibroPorId(Long id) {
        return repositorioLibro.buscarLibroPorId(id);
    }
}