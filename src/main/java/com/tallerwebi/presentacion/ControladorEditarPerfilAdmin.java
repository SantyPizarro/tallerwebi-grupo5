package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorEditarPerfilAdmin {

    @GetMapping("/editar-perfil-admin")
    public ModelAndView mostrarPerfil(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario != null) {
            ModelMap model = new ModelMap();
            model.put("usuario", usuario);

            return new ModelAndView("editar-perfil-admin", model);
        }
        return new ModelAndView("redirect:/perfilAdmin");
    }

}
