package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<Libro> obtenerListaDeGeneros(List<String> generos){
        return repositorioLibro.obtenerListaDeGeneros(generos);
    }

    private Libro buscarLibroPorTitulo(String titulo) {
        return repositorioLibro.buscarUnLibroPorSuTitulo(titulo);
    }

    public void agregarLibro(DatosLibro datosLibro, MultipartFile foto) throws LibroExistente {
        Libro libro = buscarLibroPorTitulo(datosLibro.getTitulo());

        if (libro != null) {
            throw new LibroExistente();
        } else {
            String rutaImagen = "";
            if (!foto.isEmpty()) {
                try {
                    // Obtener la ruta del directorio actual y construir la ruta absoluta
                    String currentDir = System.getProperty("user.dir");
                    String imagesDir = currentDir + "/src/main/webapp/resources/core/images/libros/";
                    Path rutaAbsoluta = Paths.get(imagesDir + foto.getOriginalFilename());
                    Files.createDirectories(rutaAbsoluta.getParent());

                    // Escribir el archivo
                    byte[] bytes = foto.getBytes();
                    Files.write(rutaAbsoluta, bytes);

                    // Establecer la ruta de la imagen
                    rutaImagen = "/spring/images/libros/" + foto.getOriginalFilename();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Manejar la excepción de manera adecuada, por ejemplo, lanzar una nueva excepción o registrar el error
                }
            }
            Genero genero = repositorioLibro.buscarUnGeneroPorId(datosLibro.getGenero());
            // Crear el objeto Libro y establecer la ruta de la imagen
            Libro libroAgregar = new Libro(datosLibro.getTitulo(), datosLibro.getAutor(), datosLibro.getEditorial(), datosLibro.getFechaPublicacion(), datosLibro.getPrecio(), datosLibro.getDescripcion(), rutaImagen, genero);
            repositorioLibro.agregar(libroAgregar);
        }
    }

    public void eliminarLibro(String titulo){
        repositorioLibro.eliminarLibro(repositorioLibro.buscarUnLibroPorSuTitulo(titulo));
    }



}
