package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PerfilService;
import com.tallerwebi.dominio.RepositorioUsuario;
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
    private RepositorioUsuario repositorioUsuario;



    @Autowired
    public ControladorPerfil(PerfilService perfilService,RepositorioUsuario repositorioUsuario) {
        this.perfilService = perfilService;

        this.repositorioUsuario = repositorioUsuario;
    }

    @GetMapping("/perfil")
    public ModelAndView mostrarPerfil() {

        Usuario usuario = perfilService.buscarUsuario("test@unlam.edu.ar", "test");
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);

        return new ModelAndView("perfil", model);
    }

    /*@PostMapping("/perfil/editar")
    public String editarDescripcion(@ModelAttribute("usuario") Usuario usuario) {
        Usuario usuarioExistente = perfilService.buscarUsuario("test@unlam.edu.ar", "test");
        perfilService.editarDescripcionPerfil(usuario.getDescripcion(), usuarioExistente);

        return "redirect:/perfil";
    }*/

/*
    @PostMapping("/perfil/editarPerfil")
    public String editarPerfil(@ModelAttribute("usuario") Usuario usuario) {
        Usuario usuarioExistente = perfilService.buscarUsuario("test@unlam.edu.ar", "test");
        perfilService.editarUsuario(usuario, usuarioExistente);

        return "redirect:/perfil";
    }*/


    @PostMapping("/perfil/editarPerfilCompleto")
    public String editarPerfilCompleto(@RequestParam(name = "file", required = false) MultipartFile foto,
                                       @ModelAttribute("usuario") Usuario usuario,
                                       @RequestParam(name = "id") Long id,
                                       RedirectAttributes flash) {

        Usuario usuarioExistente=perfilService.buscarUsuarioPorId(id);
        if (usuarioExistente == null) {
            // Manejar el caso donde el usuario no se encuentra
            flash.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/perfil";
        }


        try{
            perfilService.editarPerfilCompleto(usuarioExistente,usuario , foto);
            flash.addFlashAttribute("success", "Usuario modificado");
        }catch (Exception e){
            flash.addFlashAttribute("error", "Error al guardar la foto: " + e.getMessage());
            return "redirect:/login";
        }

        return "redirect:/perfil";
    }


/*
    @PostMapping("/perfil/editarFoto")
    @Transactional
    public String editarFoto(@RequestParam(name = "file", required = false) MultipartFile foto,
                             @RequestParam(name = "id") Long id,
                             RedirectAttributes flash) {
        Usuario usuario = repositorioUsuario.buscarPorId(id); // Cargar usuario desde la base de datos usando el ID

        if (usuario == null) {
            // Manejar el caso donde el usuario no se encuentra
            flash.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/perfil";
        }

        if (!foto.isEmpty()) {
            try {
                // Obtener la ruta del directorio actual y construir la ruta absoluta
                String currentDir = System.getProperty("user.dir");
                String imagesDir = currentDir + "/src/main/webapp/resources/core/images/";

                // Crear los directorios si no existen
                Path rutaAbsoluta = Paths.get(imagesDir + foto.getOriginalFilename());
                Files.createDirectories(rutaAbsoluta.getParent());

                // Escribir el archivo
                byte[] bytes = foto.getBytes();
                Files.write(rutaAbsoluta, bytes);

                // Actualizar la información del usuario
                usuario.setFoto(foto.getOriginalFilename());
                repositorioUsuario.modificar(usuario);

                flash.addFlashAttribute("success", "Usuario modificado");
            } catch (Exception e) {
                e.printStackTrace(); // Log de la excepción para depuración
                flash.addFlashAttribute("error", "Error al guardar la foto: " + e.getMessage());
                return "redirect:/login";
            }
        }

        return "redirect:/perfil";
    }*/


}
