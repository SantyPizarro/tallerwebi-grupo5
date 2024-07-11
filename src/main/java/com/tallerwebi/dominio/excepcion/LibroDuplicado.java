package com.tallerwebi.dominio.excepcion;

public class LibroDuplicado extends Throwable {
    public LibroDuplicado() {
        super();
    }

    public LibroDuplicado(String message) {
        super(message);
    }
}
