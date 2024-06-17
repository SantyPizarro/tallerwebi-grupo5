package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPreferencias {

    List<Libro> recomendarLibros(Preferencias preferencias);
}
