package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;

public interface PlanService {

    void planFree(Usuario usuario);

    void comprarPlanBasico(Usuario usuario) throws PlanYaAdquiridoException;
    void comprarPlanEstandar(Usuario usuario) throws PlanYaAdquiridoException;
    void comprarPlanPremium(Usuario usuario) throws PlanYaAdquiridoException;

    void beneficioCuponPlan(Usuario usuario, Integer porcentaje);

    void aplicarBeneficioPlanBasico(Usuario usuario);
    void aplicarBeneficioPlanEstandar(Usuario usuario);
    void aplicarBeneficioPlanPremium(Usuario usuario);

    String descripcionPlanes(Long id);

    void verificarYActualizarPlanesExpirados();

    Boolean verificarPlan(Usuario usuario);
   /* void actualizarPlanUsuario(Usuario usuario,Long planId);
    void aplicarBeneficio(Usuario usuario);
*/

}
