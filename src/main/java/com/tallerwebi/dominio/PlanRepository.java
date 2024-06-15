package com.tallerwebi.dominio;


public interface PlanRepository {

    void guardarPlan(Plan plan);
    Plan buscarPlan(Long id);

}
