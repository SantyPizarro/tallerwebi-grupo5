package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PerfilService;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfil {

    private PerfilService perfilService;


    @Autowired
    public ControladorPerfil(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/perfil")
    public ModelAndView mostrarPerfil() {

        Usuario usuario = perfilService.buscarUsuario("test@unlam.edu.ar");
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);

        return new ModelAndView("perfil", model);
    }

    @PostMapping("/perfil/editar")
    public String editarDescripcion(@ModelAttribute("usuario") Usuario usuario) {
        Usuario usuarioExistente = perfilService.buscarUsuario("test@unlam.edu.ar");
        perfilService.editarDescripcionPerfil(usuario.getDescripcion(), usuarioExistente);

        return "redirect:/perfil";
    }


    @PostMapping("/perfil/editarPerfil")
    public String editarPerfil(@ModelAttribute("usuario") Usuario usuario) {
        Usuario usuarioExistente = perfilService.buscarUsuario("test@unlam.edu.ar");
        perfilService.editarUsuario(usuario, usuarioExistente);

        return "redirect:/perfil";
    }

}
