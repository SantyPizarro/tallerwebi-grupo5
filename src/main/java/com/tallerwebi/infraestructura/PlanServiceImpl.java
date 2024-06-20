package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepository planRepository;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioLibro repositorioLibro;
    private RepositorioCupon repositorioCupon;
    private RepositorioTipoPlan repositorioTipoPlan;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, RepositorioUsuario repositorioUsuario, RepositorioLibro repositorioLibro, RepositorioCupon repositorioCupon, RepositorioTipoPlan repositorioTipoPlan) {
        this.planRepository = planRepository;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioLibro = repositorioLibro;
        this.repositorioCupon = repositorioCupon;
        this.repositorioTipoPlan = repositorioTipoPlan;
    }

    @Override
    public void planFree(Usuario usuario) {
        usuario.setPlan(planRepository.buscarPlan(1L));
    }

    @Override
    public void comprarPlanBasico(Usuario usuario) throws PlanYaAdquiridoException {
        if (verificarPlan(usuario)) {
            usuario.setPlan(planRepository.buscarPlan(2L));
            usuario.getPlan().setFechaCompra(LocalDateTime.now());
            usuario.getPlan().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
            repositorioUsuario.modificar(usuario);
        } else {
            throw new PlanYaAdquiridoException();
        }
    }

    @Override
    public void comprarPlanEstandar(Usuario usuario) throws PlanYaAdquiridoException {
        if (verificarPlan(usuario)) {
            usuario.setPlan(planRepository.buscarPlan(3L));
            usuario.getPlan().setFechaCompra(LocalDateTime.now());
            usuario.getPlan().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
            repositorioUsuario.modificar(usuario);
        } else {
            throw new PlanYaAdquiridoException();
        }
    }

    @Override
    public void comprarPlanPremium(Usuario usuario) throws PlanYaAdquiridoException {
        if (verificarPlan(usuario)) {
            usuario.setPlan(planRepository.buscarPlan(4L));
            usuario.getPlan().setFechaCompra(LocalDateTime.now());
            usuario.getPlan().setFechaVencimiento(LocalDateTime.now().plusMonths(1));
            repositorioUsuario.modificar(usuario);
        } else {
            throw new PlanYaAdquiridoException();
        }
    }

    @Override
    public void beneficioCuponPlan(Usuario usuario, Integer porcentaje) {
        Cupon a1 = new Cupon(porcentaje);
        a1.setCodigo(generateRandomString());
        repositorioCupon.guardarCupon(a1);
        CodigoDescuento codigos = new CodigoDescuento();
        codigos.getCuponesDescuento().add(a1);
        repositorioCupon.modificarCodigoDescuento(codigos);
        usuario.getCuponesDeDescuento().add(a1);
    }

    @Override
    public void aplicarBeneficioPlanBasico(Usuario usuario) {
        if (usuario != null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("basic")) {
            beneficioCuponPlan(usuario, 20);
            repositorioUsuario.modificar(usuario);
        }
    }


    @Override
    public void aplicarBeneficioPlanEstandar(Usuario usuario) {
        if (usuario != null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("standard")) {
            beneficioCuponPlan(usuario, 25);
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(1L));
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(2L));
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void aplicarBeneficioPlanPremium(Usuario usuario) {
        if (usuario != null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("premium")) {
            beneficioCuponPlan(usuario, 30);
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(1L));
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(2L));
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(3L));
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(4L));
            usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(5L));
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public String descripcionPlanes(Long id) {
        return repositorioTipoPlan.obtenerDescripcionPlanes(id);
    }


    private Boolean verificarPlan(Usuario usuario) {
        return usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("free");
    }


    /*
    @Override
    public void actualizarPlanUsuario(Usuario usuario, Long planId) {
        Plan nuevoPlan = planRepository.buscarPlan(planId);

        usuario.setPlan(nuevoPlan);

        aplicarBeneficio(usuario);

        repositorioUsuario.modificar(usuario);

    }*/
    /*
    @Override
    public void aplicarBeneficio(Usuario usuario) {
        Set<Libro> librosBeneficio = new HashSet<>();
        librosBeneficio.add(repositorioLibro.buscarLibroPorId(1L));
        librosBeneficio.add(repositorioLibro.buscarLibroPorId(2L));
        librosBeneficio.add(repositorioLibro.buscarLibroPorId(3L));

        if(usuario!=null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("basic")) {

            }
        }

    }*/

    public String generateRandomString() {
        int length = 6 + RANDOM.nextInt(3); // Genera un número entre 6 y 8 inclusive
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }

        return sb.toString();
    }



    /*
    public void otorgarCuponesCadaDosCompras(Usuario usuario){

    if(fecha_compra_plan > fecha_compra_libro && fecha_vencimiento_plan < fecha_compra_libro){


        // Obtener el número total de compras del usuario
        int totalCompras = compraRepository.countByUsuarioId(usuario.getId());

        // Calcular cupones a otorgar
        int cuponesNuevos = totalCompras / 2;

        // Obtener el número actual de cupones
        int cuponesActuales = usuario.getCuponesDeDescuento().size();

        // Calcular cuántos cupones adicionales se necesitan
        int cuponesAAgregar = cuponesNuevos - cuponesActuales;

        // Crear y agregar los cupones al usuario si es necesario
        for (int i = 0; i < cuponesAAgregar; i++) {
            Cupon cupon = new Cupon();
            cupon.setCodigo(generateRandomString());
            usuario.getCuponesDeDescuento().add(cupon);
        }

        // Guardar el usuario actualizado
            usuarioRepository.modificar(usuario);
    }
}
    }


            */

}
