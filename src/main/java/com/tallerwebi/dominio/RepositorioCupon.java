package com.tallerwebi.dominio;

public interface RepositorioCupon {

    void guardarCupon(Cupon cupon);
    void modificarCodigoDescuento(CodigoDescuento codigoDescuento);

    Cupon buscarCuponPorId(Long id);

    void eliminarCupon(Cupon cupon);
}
