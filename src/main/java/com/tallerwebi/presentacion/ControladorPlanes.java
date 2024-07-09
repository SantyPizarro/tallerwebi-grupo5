package com.tallerwebi.presentacion;

import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PlanYaAdquiridoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorPlanes {

    private final PlanService planService;
    private final MercadoPagoService mercadoPagoService;


    @Autowired
    public ControladorPlanes(MercadoPagoService mercadoPagoService, PlanService planService) {
        this.planService = planService;
        this.mercadoPagoService = mercadoPagoService;
    }

    @GetMapping("/planes")
    public ModelAndView planes(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario != null) {
            ModelMap modelo = new ModelMap();
            modelo.addAttribute("free", planService.descripcionPlanes(1L));
            modelo.addAttribute("basic", planService.descripcionPlanes(2L));
            modelo.addAttribute("estandar", planService.descripcionPlanes(3L));
            modelo.addAttribute("premium", planService.descripcionPlanes(4L));

            return new ModelAndView("planes", modelo);
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/comprarPlan")
    public ModelAndView comprarPlan(@RequestParam("nombrePlan") String nombrePlan,HttpServletRequest request, RedirectAttributes flash) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        Double precio = 0.0d;
        session.setAttribute("nombrePlan", nombrePlan);

        if (nombrePlan.equalsIgnoreCase("basic")) {
            precio = 10000D;
        }
        if (nombrePlan.equalsIgnoreCase("estandar")) {
            precio = 25000D;
        }
        if (nombrePlan.equalsIgnoreCase("premium")) {
            precio = 39999D;
        }

        if (usuario.getPlan().getTipoPlan().getNombre().equals("free")) {

            Preference preference = mercadoPagoService.createPreferencePlan(usuario, precio);

            if (preference != null) {
                return new ModelAndView("redirect:" + preference.getSandboxInitPoint());
            }

        } else {
            flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
        }
        return new ModelAndView("redirect:/planes");
    }


    @GetMapping("/payment/feedbackPlan")
    public ModelAndView feedback(@RequestParam("collection_status") String paymentStatus,
                                 HttpServletRequest request, RedirectAttributes flash) {

        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");

        if ("approved".equals(paymentStatus)) {
            String nombre = (String) sesion.getAttribute("nombrePlan");

            switch (nombre){
                case "basic": beneficiosPlanBasico(usuario, flash);
                    break;
                case "estandar": beneficiosPlanEstandar(usuario, flash);
                    break;
                case "premium": beneficiosPlanPremium(usuario, flash);
                    break;
            }
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("redirect:/planes");
    }

    private void beneficiosPlanPremium(Usuario usuario, RedirectAttributes flash) {
        if (usuario != null) {
            try {
                planService.comprarPlanPremium(usuario);
                planService.aplicarBeneficioPlanPremium(usuario);
            } catch (PlanYaAdquiridoException e) {
                flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
            }
        }
    }

    private void beneficiosPlanEstandar(Usuario usuario, RedirectAttributes flash) {
        if (usuario != null) {
                try {
                    planService.comprarPlanEstandar(usuario);
                    planService.aplicarBeneficioPlanEstandar(usuario);
                } catch (PlanYaAdquiridoException e) {
                    flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
                }
            }
    }

    private void beneficiosPlanBasico(Usuario usuario, RedirectAttributes flash) {
        if (usuario != null) {
            try {
                planService.comprarPlanBasico(usuario);
                planService.aplicarBeneficioPlanBasico(usuario);
            } catch (PlanYaAdquiridoException e) {
                flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
            }
        }
    }
}