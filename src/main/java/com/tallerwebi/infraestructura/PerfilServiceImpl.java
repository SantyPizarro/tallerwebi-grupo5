package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private RepositorioUsuario repositorioUsuario;
    private PlanRepository planRepository;

    @Autowired
    public PerfilServiceImpl(RepositorioUsuario repositorioUsuario, PlanRepository planRepository) {
        this.repositorioUsuario = repositorioUsuario;
        this.planRepository = planRepository;
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

    public void addLibroFavorito(Usuario usuario, Libro libro) throws LibroExistente {

        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosFavoritos = usuario.getLibrosFavoritos();


        if (!librosFavoritos.contains(libro)) {
            librosFavoritos.add(libro);
            repositorioUsuario.modificar(usuario);
        }else{
            throw new LibroExistente();
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
    public void addLibroDeseado(Usuario usuario, Libro libro) throws LibroExistente {

        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("Usuario o libro no pueden ser nulos");
        }

        Set<Libro> librosDeseados = usuario.getLibrosDeseados();


        if (!librosDeseados.contains(libro) && !usuario.getLibrosComprados().contains(libro)) {
            librosDeseados.add(libro);
            repositorioUsuario.modificar(usuario);
        }else{
            throw new LibroExistente();
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

    @Override
    public List<Compra> historialDeCompras(Usuario usuario) {
        return repositorioUsuario.historialDeCompras(usuario);
    }

    @Override
    public Set<Usuario> buscarAmigos(Usuario usuario) {
        return repositorioUsuario.buscarAmigos(usuario);
    }

    @Override
    public Set<Libro> buscarMisLibros(Usuario usuario) {
        return repositorioUsuario.buscarMisLibros(usuario);
    }

    @Override
    public Boolean verificarPlan(Usuario usuario) {
        return planRepository.verificarPlanDeUsuario(usuario)!=null;
    }

    @Override
    public Set<Libro> buscarLibrosDePlan(Usuario usuario) {
        return usuario.getLibrosPlan();
    }
}
