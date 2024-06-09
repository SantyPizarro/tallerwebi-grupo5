package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAmigos {

    private UsuarioService usuarioService;

    @Autowired
    public ControladorAmigos(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/mostrarAmigos")
    public ModelAndView mostrarAmigos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        if (usuario != null) {
            ModelMap model = new ModelMap();
            model.put("usuarios", usuarioService.listar(usuario));
            return new ModelAndView("amigos",model);
        }
        return new ModelAndView("redirect:/login");
    }
}
