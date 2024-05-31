package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicioLibro {

    private final RepositorioLibro repositorioLibro;

    @Autowired
    public ServicioLibro(RepositorioLibro repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    public List<Libro> obtenerTodosLosLibros(){
        return repositorioLibro.obtenerTodosLosLibros();
    }

    public List<String> obtenerEditoriales(){
        return repositorioLibro.obtenerEditoriales();
    }

    public List<Libro> ordenarPorFechaAgregado(){
        return repositorioLibro.ordenarPorFechaAgregado();
    }

    public Libro mostrarDetalleLibro(String titulo){
        return repositorioLibro.buscarUnLibroPorSuTitulo(titulo);
    }

    public List<Libro> filtrarLibros(String editorial, Double precioMinimo, Double precioMaximo, String genero){
        return repositorioLibro.filtrarLibros(editorial, precioMinimo,precioMaximo, genero);
    }

    public List<String> obtenerGeneros(){
        return repositorioLibro.obtenerGeneros();
    }

    private Libro buscarLibroPorTitulo(String titulo) {
        return repositorioLibro.buscarUnLibroPorSuTitulo(titulo);
    }

    public void agregarLibro(DatosLibro datosLibro) throws LibroExistente {
       Libro libro = buscarLibroPorTitulo(datosLibro.getTitulo());

       if (libro != null){
           throw new LibroExistente();
       } else {
           Libro libroAgregar = new Libro(datosLibro.getTitulo(), datosLibro.getAutor(), datosLibro.getEditorial(), datosLibro.getFechaPublicacion(),datosLibro.getPrecio(),datosLibro.getDescripcion());
           repositorioLibro.agregar(libroAgregar);
       }
    }

}
