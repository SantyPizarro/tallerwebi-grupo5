package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBarraBusqueda {
    List<Libro> buscarPorTituloOAutor(String titulo, String autor);

}
