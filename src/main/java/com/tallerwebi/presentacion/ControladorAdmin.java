package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.LibroNoExiste;
import com.tallerwebi.infraestructura.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAdmin {

    private final ServicioLibro servicioLibro;
    private final UsuarioService usuarioService;

    @Autowired
    public ControladorAdmin(ServicioLibro servicioLibro, UsuarioService usuarioService) {
        this.servicioLibro = servicioLibro;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfilAdmin")
    public ModelAndView perfilAdmin(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {
            ModelMap model = new ModelMap();
            DatosLibro datosLibro = new DatosLibro();

            model.put("usuarios",usuarioService.mostrarUsers());
            model.put("usuariosAdmin",usuarioService.mostrarAdmins());
            model.put("usuario", usuario);

            model.put("datosLibro", datosLibro);
            model.put("libros", servicioLibro.obtenerTodosLosLibros());
            return new ModelAndView("perfil-admin", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/agregarLibroABDD", method = RequestMethod.POST)
    public ModelAndView agregarLibroABDD(@ModelAttribute("datosLibro") DatosLibro datosLibro,
                                         @RequestParam(name= "file", required = false) MultipartFile foto) throws LibroExistente {

        servicioLibro.agregarLibro(datosLibro, foto);

        return new ModelAndView ("redirect:/perfilAdmin");
    }

    @PostMapping(path = "/eliminarLibroDeBDD")
    public String eliminarLibroDeBDD(@ModelAttribute("titulo") String titulo, RedirectAttributes redirectAttributes) {
        try {
            servicioLibro.eliminarLibro(titulo);
            redirectAttributes.addFlashAttribute("exitoso", "Libro borrado exitosamente");
        } catch (LibroNoExiste e) {
            redirectAttributes.addFlashAttribute("error", "No existe libro");
        }

        return "redirect:/perfilAdmin";
    }

    @RequestMapping(path = "/eliminarUsuario", method = RequestMethod.POST)
    public ModelAndView eliminarUsuarioDeBDD(@ModelAttribute("idUsuario") Long idUsuario){

        usuarioService.eliminarUsuario(usuarioService.buscarPorId(idUsuario));

        return new ModelAndView ("redirect:/perfilAdmin");
    }



}
