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
    public ModelAndView mostrarPerfil() {
        Libro libro = new Libro();
        Usuario usuario = perfilService.buscarUsuario("test@unlam.edu.ar", "test");
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        model.put("libro", libro);
        return new ModelAndView("perfil", model);
    }


    @PostMapping("/perfil/editarPerfilCompleto")
    public String editarPerfilCompleto(@RequestParam(name = "file", required = false) MultipartFile foto,
                                       @ModelAttribute("usuario") Usuario usuario,
                                       @RequestParam(name = "id") Long id,
                                       RedirectAttributes flash) {

        Usuario usuarioExistente = perfilService.buscarUsuarioPorId(id);
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
    public String agregarLibro(@RequestParam("titulo")String titulo) {
        servicioLibro.mostrarDetalleLibro(titulo);

        return "redirect:/perfil";
    }

}
