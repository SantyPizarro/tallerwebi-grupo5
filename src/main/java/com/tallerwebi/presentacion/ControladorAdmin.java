package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import com.tallerwebi.dominio.excepcion.LibroNoExiste;
import com.tallerwebi.infraestructura.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorAdmin {

    private final ServicioLibro servicioLibro;
    private final UsuarioService usuarioService;
    private static final String DIRECTORIOSLIDERHEROES = "src/main/webapp/resources/core/images/slider-heroes/";


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
            model.put("librosDestacados", servicioLibro.getLibrosDestacados());
            model.put("usuarios",usuarioService.mostrarUsers());
            model.put("usuariosAdmin",usuarioService.mostrarAdmins());
            model.put("usuario", usuario);
            model.put("sliderHeroes", obtenerImagenesSliderHeroes());

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

    @PostMapping("/agregarLibroDestacado")
    public String agregarLibro(@RequestParam Long libroId) {
        servicioLibro.agregarLibroALibrosDestacados(libroId);
        return "redirect:/perfilAdmin";
    }

    @PostMapping("/eliminarLibroDestacado")
    public String eliminarLibro(@RequestParam Long libroId) {
        servicioLibro.eliminarLibroDeLibrosDestacados(libroId);
        return "redirect:/perfilAdmin";
    }

    @PostMapping("/editarSliderHero")
    public String editarSliderHero(@RequestParam(name = "sliderHero", required = false) MultipartFile sliderHero, Model model) throws IOException {
        if (sliderHero.isEmpty()) {
            model.addAttribute("mensaje", "No se ha seleccionado ningún archivo");
            return "redirect:/perfilAdmin";
        }

        byte[] bytes = sliderHero.getBytes();
        Path path = Paths.get(DIRECTORIOSLIDERHEROES + sliderHero.getOriginalFilename());
        Files.write(path, bytes);

        model.addAttribute("sliderHero", sliderHero.getOriginalFilename());
        model.addAttribute("mensaje", "El archivo " + sliderHero.getOriginalFilename() + " se ha guardado exitosamente");

        return "redirect:/perfilAdmin";
    }

    @PostMapping("/eliminarSliderHero")
    public String eliminarSliderHero(@RequestParam("imageName") String imageName, Model model) throws IOException {
        Path path = Paths.get(DIRECTORIOSLIDERHEROES + imageName);
        boolean eliminado = Files.deleteIfExists(path);

        if (eliminado) {
            model.addAttribute("mensaje", "La imagen " + imageName + " ha sido eliminada");
        } else {
            model.addAttribute("mensaje", "La imagen " + imageName + " no pudo ser eliminada");
        }

        return "redirect:/perfilAdmin";
    }

    private List<String> obtenerImagenesSliderHeroes() {
        File carpeta = new File(DIRECTORIOSLIDERHEROES);
        File[] archivos = carpeta.listFiles();
        List<String> nombresArchivos = new ArrayList<>();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && (archivo.getName().toLowerCase().endsWith(".jpg") || archivo.getName().toLowerCase().endsWith(".png"))) {
                    nombresArchivos.add(archivo.getName());
                }
            }
        }
        return nombresArchivos;
    }
}
