package com.tallerwebi.infraestructura;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.tallerwebi.dominio.Genero;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.RepositorioLibro;
import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;

@SpringJUnitConfig(HibernateTestConfig.class)
@Transactional
public class RepositorioLibroImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioLibro repositorioLibro; // Aquí debería ser autowired el repositorio real, no un mock

    private ServicioLibro servicioLibro;
    private RepositorioLibroImpl repositorioLibroImpl;

    @BeforeEach
    public void init() {
        insertarDatosPrueba();

        servicioLibro = new ServicioLibro(repositorioLibro); // Instancia real del servicio, no mock
        repositorioLibroImpl = new RepositorioLibroImpl(sessionFactory); // Instancia real del repositorio, no mock


    }

   @Test
    public void SePuedenFiltrarLosLibrosPorGenero() {

       List<Libro> librosEsperados = new ArrayList<>();
       librosEsperados.add(new Libro("El Principito", new Genero("Cuento")));
       librosEsperados.add(new Libro("El Alquimista", new Genero("Cuento")));
       librosEsperados.add(new Libro("100 Años de Soledad", new Genero("Cuento")));

       Session session = sessionFactory.getCurrentSession();
        String genero = "Cuento";

        List<Libro> resultado = repositorioLibroImpl.filtrarLibros(null, null, null, genero);

        System.out.println(librosEsperados);
        System.out.println(resultado);

        assertEquals(librosEsperados.size(), resultado.size());
        assertEquals(librosEsperados.get(0).getGenero().getNombre(), resultado.get(0).getGenero().getNombre());
        assertEquals(librosEsperados.get(1).getGenero().getNombre(), resultado.get(1).getGenero().getNombre());
        assertEquals(librosEsperados.get(2).getGenero().getNombre(), resultado.get(2).getGenero().getNombre());


   }

    @Test
    public void SePuedenFiltrarLosLibrosPorGeneroYPrecio() {

        List<Libro> librosEsperados = new ArrayList<>();
        librosEsperados.add(new Libro("El Principito", 5500.0, new Genero("Cuento")));
        librosEsperados.add(new Libro("Don Quijote de la Mancha", 8000.0,  new Genero("Cuento")));

        Session session = sessionFactory.getCurrentSession();
        String genero = "Cuento";

        List<Libro> resultado = repositorioLibroImpl.filtrarLibros(null, 5000.0, 10000.0, genero);

        System.out.println(librosEsperados);
        System.out.println(resultado);

        assertEquals(librosEsperados.size(), resultado.size());
        assertEquals(librosEsperados.get(0).getGenero().getNombre(), resultado.get(0).getGenero().getNombre());
        assertEquals(librosEsperados.get(1).getGenero().getNombre(), resultado.get(1).getGenero().getNombre());
        assertEquals(librosEsperados.get(0).getPrecio(), resultado.get(0).getPrecio());
        assertEquals(librosEsperados.get(1).getPrecio(), resultado.get(1).getPrecio());
    }

    @Test
    public void SePuedenFiltrarLosLibrosPorGeneroPrecioYEditorial() {
        String editorial = "Planeta";

        List<Libro> librosEsperados = new ArrayList<>();
        librosEsperados.add(new Libro("El Principito", 5500.0, editorial, new Genero("Cuento")));
        librosEsperados.add(new Libro("La Sombra del Viento", 2500.0, editorial,  new Genero("Cuento")));

        Session session = sessionFactory.getCurrentSession();
        String genero = "Cuento";


        List<Libro> resultado = repositorioLibroImpl.filtrarLibros(editorial, 2000.0, 10000.0, genero);

        System.out.println(librosEsperados);
        System.out.println(resultado);

        assertEquals(librosEsperados.size(), resultado.size());
        assertEquals(librosEsperados.get(0).getGenero().getNombre(), resultado.get(0).getGenero().getNombre());
        assertEquals(librosEsperados.get(1).getGenero().getNombre(), resultado.get(1).getGenero().getNombre());
        assertEquals(librosEsperados.get(0).getPrecio(), resultado.get(0).getPrecio());
        assertEquals(librosEsperados.get(1).getPrecio(), resultado.get(1).getPrecio());
        assertEquals(librosEsperados.get(0).getEditorial(), resultado.get(0).getEditorial());
        assertEquals(librosEsperados.get(1).getEditorial(), resultado.get(1).getEditorial());
    }

    @Test
    public void SePuedeBuscarUnLibroPorSuTitulo() {
        String titulo = "El Principito";

        assertEquals(titulo, repositorioLibroImpl.buscarUnLibroPorSuTitulo(titulo).getTitulo());
    }

    @Test
    public void SePuedeBuscarUnGeneroPorSuId() {

        Genero generoEsperado = new Genero(2L,"Cuento");
        assertEquals(generoEsperado.getNombre(), repositorioLibroImpl.buscarUnGeneroPorId(2L).getNombre());
    }

    @Test
    public void SePuedeBuscarUnLibroPorSuId(){
        Libro libroEsperado = new Libro(1L, "El Principito", new Genero("Cuento"));

        assertEquals(libroEsperado.getTitulo(), repositorioLibroImpl.buscarLibroPorId(1L).getTitulo());
    }

    @Test
    public void queLosLibrosSeOrdenenCorrectamente(){
        List<Libro> librosEsperados = new ArrayList<>();

        librosEsperados.add(new Libro ("El Alquimista", "2024-08-01"));
        librosEsperados.add(new Libro ("El Chico que Bajo las Estrellas", "2024-07-01"));
        librosEsperados.add(new Libro ("La Sombra del Viento', 'Editorial Planeta", "2024-06-01"));

        List<Libro> librosOrdenados = repositorioLibroImpl.ordenarPorFechaAgregado();

        List<Libro> primerosTresLibrosOrdenados = librosOrdenados.subList(0, 2);

        assertEquals(librosEsperados.get(0).getFechaAgregado(), primerosTresLibrosOrdenados.get(0).getFechaAgregado());

    }


    private void insertarDatosPrueba() /* session.save() no me guardaba nada */ {
        Session session = sessionFactory.getCurrentSession();

        session.createNativeQuery("INSERT INTO Genero (id, nombre) VALUES (1, 'Ficcion')").executeUpdate();
        session.createNativeQuery("INSERT INTO Genero (id, nombre) VALUES (2, 'Cuento')").executeUpdate();
        session.createNativeQuery("INSERT INTO Genero (id, nombre) VALUES (3, 'Novela')").executeUpdate();
        session.createNativeQuery("INSERT INTO Genero (id, nombre) VALUES (4, 'Poesia')").executeUpdate();

        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('El Principito', 'Planeta', '2024-01-01', 5500, 2)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('Don Quijote de la Mancha', 'Anaya', '2024-02-01', 8000, 2)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('Martín Fierro', 'Losada', '2024-03-01', 12000, 3)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('El Fantasma de Canterville', 'Alianza Editorial', '2024-04-01', 20000, 4)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('El Jardín Secreto', 'Salamandra', '2024-05-01', 7300, 4)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('La Sombra del Viento', 'Planeta', '2024-06-01', 2500, 2)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('El Chico que Bajo las Estrellas', 'Random House', '2024-07-01', 15000, 3)").executeUpdate();
        session.createNativeQuery("INSERT INTO Libro (titulo, editorial, fechaAgregado, precio, genero_id) VALUES ('El Alquimista', 'Anaya', '2024-08-01', 1000, 1)").executeUpdate();
    }
}

