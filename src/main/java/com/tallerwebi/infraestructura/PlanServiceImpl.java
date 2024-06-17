package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepository planRepository;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioLibro repositorioLibro;
    private RepositorioCupon repositorioCupon;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, RepositorioUsuario repositorioUsuario, RepositorioLibro repositorioLibro,RepositorioCupon repositorioCupon) {
        this.planRepository=planRepository;
        this.repositorioUsuario=repositorioUsuario;
        this.repositorioLibro=repositorioLibro;
        this.repositorioCupon=repositorioCupon;
    }

    @Override
    public void comprarPlanBasico(Usuario usuario) {
        usuario.setPlan(planRepository.buscarPlan(1L));
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void comprarPlanEstandar(Usuario usuario) {
        usuario.setPlan(planRepository.buscarPlan(2L));
        repositorioUsuario.modificar(usuario);

    }

    @Override
    public void comprarPlanPremium(Usuario usuario) {
        usuario.setPlan(planRepository.buscarPlan(3L));
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void aplicarBeneficioPlanBasico(Usuario usuario) {
           if(usuario!=null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("basic")) {
            Cupon a1 = new Cupon(30);
            a1.setCodigo(generateRandomString());
            repositorioCupon.guardarCupon(a1);
            CodigoDescuento codigos = new CodigoDescuento();
            codigos.getCuponesDescuento().add(a1);
            repositorioCupon.modificarCodigoDescuento(codigos);
            usuario.getCuponesDeDescuento().add(a1);
            repositorioUsuario.modificar(usuario);
           }
    }

    @Override
    public void aplicarBeneficioPlanEstandar(Usuario usuario) {
        if(usuario!=null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("standard")) {
        aplicarBeneficioPlanBasico(usuario);
        usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(1L));
        usuario.getLibrosPlan().add(repositorioLibro.buscarLibroPorId(2L));
        repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void aplicarBeneficioPlanPremium(Usuario usuario) {
        if(usuario!=null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("premium")) {
            usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(1L));
            usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(2L));
            usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(3L));
            repositorioUsuario.modificar(usuario);
        }
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

}
