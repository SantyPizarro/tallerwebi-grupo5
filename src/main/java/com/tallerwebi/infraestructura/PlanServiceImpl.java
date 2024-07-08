package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            usuario.setCuponesEmitidos(1);
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
            usuario.setCuponesEmitidos(1);
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
            usuario.setCuponesEmitidos(1);
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
        CodigoDescuento codigos = repositorioCupon.buscarCodigoDescuento(1L);
        codigos.getCuponesDescuento().add(a1);
        repositorioCupon.modificarCodigoDescuento(codigos);
        usuario.getCuponesDeDescuento().add(a1);
        usuario.setCuponesEmitidos(usuario.getCuponesEmitidos() + 1);
        repositorioUsuario.modificar(usuario);
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



    @Override
    public Boolean verificarPlan(Usuario usuario) {
        return usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("free");
    }


    public String generateRandomString() {
        int length = 6 + RANDOM.nextInt(3); // Genera un número entre 6 y 8 inclusive
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }

        return sb.toString();
    }

    @Override
    public void verificarYActualizarPlanesExpirados() {
        List<Usuario> usuarios = repositorioUsuario.buscarTodosLosUsuarios();
        LocalDateTime now = LocalDateTime.now();
        for (Usuario usuario : usuarios) {
            if (usuario.getPlan() != null && usuario.getPlan().getFechaVencimiento() != null) {
                if (now.isAfter(usuario.getPlan().getFechaVencimiento())) {
                    usuario.setPlan(planRepository.buscarPlan(1L));
                    usuario.setCuponesDeDescuento(new HashSet<>());
                    usuario.setCuponesEmitidos(0);
                    usuario.setLibrosPlan(new HashSet<>());
                    repositorioUsuario.modificar(usuario);
                }
            }
        }
    }

}




