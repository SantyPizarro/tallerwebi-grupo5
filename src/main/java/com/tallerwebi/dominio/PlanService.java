package com.tallerwebi.dominio;

public interface PlanService {

    void planFree(Usuario usuario);

    void comprarPlanBasico(Usuario usuario);
    void comprarPlanEstandar(Usuario usuario);
    void comprarPlanPremium(Usuario usuario);

    void beneficioCuponPlan(Usuario usuario, Integer porcentaje);

    void aplicarBeneficioPlanBasico(Usuario usuario);
    void aplicarBeneficioPlanEstandar(Usuario usuario);
    void aplicarBeneficioPlanPremium(Usuario usuario);

   /* void actualizarPlanUsuario(Usuario usuario,Long planId);
    void aplicarBeneficio(Usuario usuario);
*/

}
