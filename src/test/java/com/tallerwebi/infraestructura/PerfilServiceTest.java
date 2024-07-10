package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PerfilServiceTest {
    RepositorioUsuario repositorioUsuario;
    PlanRepository planRepository;
    PerfilService perfilService;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        planRepository = mock(PlanRepository.class);
        perfilService = new PerfilServiceImpl(repositorioUsuario, planRepository);
    }


    @Test
    public void usuarioPuedeAgregarLibroFavorito() throws LibroExistente {

        Usuario usuario = new Usuario();
        usuario.setLibrosFavoritos(new HashSet<>());

        Libro libro = new Libro();
        libro.setTitulo("El Quijote");


        perfilService.addLibroFavorito(usuario, libro);


        assertTrue(usuario.getLibrosFavoritos().contains(libro));
        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    public void agregarLibroFavoritoExistenteLanzaExcepcion() {

        Usuario usuario = new Usuario();
        Set<Libro> librosFavoritos = new HashSet<>();
        Libro libro = new Libro();
        libro.setTitulo("El Quijote");
        librosFavoritos.add(libro);
        usuario.setLibrosFavoritos(librosFavoritos);

        assertThrows(LibroExistente.class, () -> {
            perfilService.addLibroFavorito(usuario, libro);
        });
    }

    @Test
    public void usuarioPuedeEliminarLibroFavorito() throws LibroExistente {

        Usuario usuario = new Usuario();
        usuario.setLibrosFavoritos(new HashSet<>());

        Libro libro = new Libro();
        libro.setTitulo("El Quijote");


        perfilService.addLibroFavorito(usuario, libro);

        perfilService.eliminarLibroFavorito(usuario, libro);


        assertFalse(usuario.getLibrosFavoritos().contains(libro));
        verify(repositorioUsuario,times(2)).modificar(usuario);
    }

    @Test
    public void usuarioPuedeEditarPerfilCompletoConFoto() throws Exception {
        Usuario usuarioExistente = new Usuario();
        Usuario usuario = new Usuario();
        usuario.setDescripcion("Nueva descripcion");
        usuario.setNombreDeUsuario("NuevoNombre");
        usuario.setGeneroFav1("Genero1");
        usuario.setGeneroFav2("Genero2");

        MultipartFile foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);
        when(foto.getOriginalFilename()).thenReturn("nueva_foto.jpg");
        when(foto.getBytes()).thenReturn("foto data".getBytes());

        Path mockPath = mock(Path.class);
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class))).thenReturn(mockPath);
            mockedFiles.when(() -> Files.write(any(Path.class), any(byte[].class))).thenReturn(mockPath);

            perfilService.editarPerfilCompleto(usuarioExistente, usuario, foto);

            assertEquals("Nueva descripcion", usuarioExistente.getDescripcion());
            assertEquals("NuevoNombre", usuarioExistente.getNombreDeUsuario());
            assertEquals("Genero1", usuarioExistente.getGeneroFav1());
            assertEquals("Genero2", usuarioExistente.getGeneroFav2());
            assertEquals("nueva_foto.jpg", usuarioExistente.getFoto());

            verify(repositorioUsuario, times(2)).modificar(usuarioExistente);
        }
    }

    @Test
    public void usuarioPuedeEditarPerfilCompletoSinFoto() {
        Usuario usuarioExistente = new Usuario();
        Usuario usuario = new Usuario();
        usuario.setDescripcion("Nueva descripcion");
        usuario.setNombreDeUsuario("NuevoNombre");
        usuario.setGeneroFav1("Genero1");
        usuario.setGeneroFav2("Genero2");

        MultipartFile foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(true);

        perfilService.editarPerfilCompleto(usuarioExistente, usuario, foto);

        assertEquals("Nueva descripcion", usuarioExistente.getDescripcion());
        assertEquals("NuevoNombre", usuarioExistente.getNombreDeUsuario());
        assertEquals("Genero1", usuarioExistente.getGeneroFav1());
        assertEquals("Genero2", usuarioExistente.getGeneroFav2());
        assertNull(usuarioExistente.getFoto());

        verify(repositorioUsuario).modificar(usuarioExistente);
    }
}
