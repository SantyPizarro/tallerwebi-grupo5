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
    private Carrito carrito;



    @Autowired
    public CarritoServiceImpl(RepositorioLibro repositorioLibro){
        this.repositorioLibro = repositorioLibro;
        carrito = new Carrito();
    }

    @Override
    public void agregarLibrosAlCarrito(Long id) throws LibroNoAgregado {
        Libro libroEncontrado = buscarLibroPorId(id);

        if(libroEncontrado != null){
            carrito.agregarLibroAlCarrito(libroEncontrado);
        }else {
            throw  new LibroNoAgregado();
        }

    }

    @Override
    public List<Libro> obtenerLibrosComprados() {
        return carrito.getLibros();
    }

    private Libro buscarLibroPorId(Long id) {
        return repositorioLibro.buscarLibroPorId(id);
    }
}
