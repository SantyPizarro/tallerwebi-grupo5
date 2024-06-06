package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.PerfilService;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public PerfilServiceImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }



    public Usuario buscarUsuario(String email, String password) {
        return repositorioUsuario.buscarUsuarioPassword(email, password);

    }

    public Usuario buscarUsuarioPorId(Long id) {
        return repositorioUsuario.buscarPorId(id);
    }




    @Override
    public void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto) {

        if (!foto.isEmpty()) {
            try {
                // Obtener la ruta del directorio actual y construir la ruta absoluta
                String currentDir = System.getProperty("user.dir");
                String imagesDir = currentDir + "/src/main/webapp/resources/core/images/";

                // Crear los directorios si no existen
                Path rutaAbsoluta = Paths.get(imagesDir + foto.getOriginalFilename());
                Files.createDirectories(rutaAbsoluta.getParent());

                // Escribir el archivo
                byte[] bytes = foto.getBytes();
                Files.write(rutaAbsoluta, bytes);

                // Actualizar la información del usuario
                usuarioExistente.setFoto(foto.getOriginalFilename());
                repositorioUsuario.modificar(usuarioExistente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        usuarioExistente.setDescripcion(usuario.getDescripcion());
        usuarioExistente.setNombreDeUsuario(usuario.getNombreDeUsuario());
        usuarioExistente.setGeneroFav1(usuario.getGeneroFav1());
        usuarioExistente.setGeneroFav2(usuario.getGeneroFav2());
        repositorioUsuario.modificar(usuarioExistente);


    }

    public void addLibroFavorito(Usuario usuario, Libro libro) {

        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosFavoritos = usuario.getLibrosFavoritos();


        if (!librosFavoritos.contains(libro)) {
            librosFavoritos.add(libro);
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void eliminarLibroFavorito(Usuario usuario, Libro libro) {
        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosFavoritos = usuario.getLibrosFavoritos();
        if (librosFavoritos.contains(libro)) {
            librosFavoritos.remove(libro);
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void addLibroDeseado(Usuario usuario, Libro libro) {

        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosDeseados = usuario.getLibrosDeseados();


        if (!librosDeseados.contains(libro)) {
            librosDeseados.add(libro);
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void eliminarLibroDeseado(Usuario usuario, Libro libro) {
        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosDeseados = usuario.getLibrosDeseados();
        if (librosDeseados.contains(libro)) {
            librosDeseados.remove(libro);
            repositorioUsuario.modificar(usuario);
        }
    }


}
