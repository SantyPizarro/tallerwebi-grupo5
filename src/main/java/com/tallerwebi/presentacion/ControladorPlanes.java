package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlanService;
import com.tallerwebi.dominio.Usuario;
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

    private PlanService planService;

    @Autowired
    public ControladorPlanes(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/planes")
    public ModelAndView planes() {
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("basic", planService.descripcionPlanes(2L));
        modelo.addAttribute("estandar", planService.descripcionPlanes(3L));
        modelo.addAttribute("premium", planService.descripcionPlanes(4L));

        return new ModelAndView("planes", modelo);
    }

    @PostMapping("/comprarPlanBasico")
    public String comprarPlanBasico(HttpServletRequest request, RedirectAttributes flash) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario != null) {
            try {
                planService.comprarPlanBasico(usuario);
                planService.aplicarBeneficioPlanBasico(usuario);
                return "redirect:/planes";
            } catch (PlanYaAdquiridoException e) {
                flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
                return "redirect:/planes";
            }
        }
        return "redirect:/login";
    }


    @PostMapping("/comprarPlanEstandar")
    public String comprarPlanEstandar(HttpServletRequest request, RedirectAttributes flash) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario != null) {
            try {
                planService.comprarPlanEstandar(usuario);
                planService.aplicarBeneficioPlanEstandar(usuario);
                return "redirect:/planes";
            } catch (PlanYaAdquiridoException e) {
                flash.addFlashAttribute("error", "El usuario ya tiene un plan activo.");
                return "redirect:/planes";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/comprarPlanPremium")
    public String comprarPlanPremium(HttpServletRequest request) throws PlanYaAdquiridoException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if(usuario != null) {
            planService.comprarPlanPremium(usuario);
            planService.aplicarBeneficioPlanPremium(usuario);
            return "redirect:/planes";
        }

        return "redirect:/login";
    }

    /*@PostMapping("/comprarPlan")
    public String comprarPlan(@RequestParam("id")Long id , HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");


        if(usuario != null) {
            planService.actualizarPlanUsuario(usuario, id);
            return "redirect:/planes";
        }

        return "redirect:/login";
    }*/

}
