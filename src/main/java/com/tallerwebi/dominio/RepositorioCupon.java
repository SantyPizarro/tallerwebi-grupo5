package com.tallerwebi.dominio;

public interface RepositorioCupon {

    void guardarCupon(Cupon cupon);
    void modificarCodigoDescuento(CodigoDescuento codigoDescuento);
    void guardarCodigoDescuento(CodigoDescuento codigoDescuento);
    CodigoDescuento buscarCodigoDescuento(Long id);

    Cupon buscarCuponPorId(Long id);

    void eliminarCupon(Cupon cupon);
}
