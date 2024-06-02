package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.PerfilService;
import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ControladorPerfil {


    private PerfilService perfilService;
    private ServicioLibro servicioLibro;


    @Autowired
    public ControladorPerfil(PerfilService perfilService,ServicioLibro servicioLibro) {
        this.perfilService = perfilService;
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("/perfil")
    public ModelAndView mostrarPerfil(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);

        return new ModelAndView("perfil", model);
    }


    @PostMapping("/perfil/editarPerfilCompleto")
    public String editarPerfilCompleto(@RequestParam(name = "file", required = false) MultipartFile foto,
                                       @ModelAttribute("usuario") Usuario usuario,
                                       RedirectAttributes flash,
                                       HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuarioExistente = (Usuario) session.getAttribute("USUARIO");
        if (usuarioExistente == null) {
            // Manejar el caso donde el usuario no se encuentra
            flash.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/perfil";
        }


        try {
            perfilService.editarPerfilCompleto(usuarioExistente, usuario, foto);
            flash.addFlashAttribute("success", "Usuario modificado");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la foto: " + e.getMessage());
            return "redirect:/login";
        }


        return "redirect:/perfil";
    }

    @PostMapping("/perfil/agregarLibro")
    public String agregarLibro(@RequestParam("titulo") String titulo, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario != null) {
            Libro libro = servicioLibro.mostrarDetalleLibro(titulo);
            Long usuarioId = usuario.getId();

            if (libro != null) {
                perfilService.addLibroFavorito(usuarioId, libro);
                if(!usuario.getLibrosFavoritos().contains(libro)) {
                    usuario.getLibrosFavoritos().add(libro);

                }
                return "redirect:/perfil";
            }
        }
        return "redirect:/perfil";
    }
}
