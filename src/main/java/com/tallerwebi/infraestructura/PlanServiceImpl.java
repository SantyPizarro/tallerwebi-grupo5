package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepository planRepository;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioLibro repositorioLibro;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, RepositorioUsuario repositorioUsuario, RepositorioLibro repositorioLibro) {
        this.planRepository=planRepository;
        this.repositorioUsuario=repositorioUsuario;
        this.repositorioLibro=repositorioLibro;
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
               if(usuario.getLibrosComprados().contains(repositorioLibro.buscarLibroPorId(2L))){
                   usuario.getLibrosComprados().remove(repositorioLibro.buscarLibroPorId(2L));
               }

               if(usuario.getLibrosComprados().contains(repositorioLibro.buscarLibroPorId(3L))){
                   usuario.getLibrosComprados().remove(repositorioLibro.buscarLibroPorId(3L));
               }

                usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(1L));
                repositorioUsuario.modificar(usuario);
           }
    }

    @Override
    public void aplicarBeneficioPlanEstandar(Usuario usuario) {
        if(usuario!=null && usuario.getPlan().getTipoPlan().getNombre().equalsIgnoreCase("standard")) {

            if(usuario.getLibrosComprados().contains(repositorioLibro.buscarLibroPorId(3L))){
                usuario.getLibrosComprados().remove(repositorioLibro.buscarLibroPorId(3L));
            }

            usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(1L));
            usuario.getLibrosComprados().add(repositorioLibro.buscarLibroPorId(2L));
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


}
