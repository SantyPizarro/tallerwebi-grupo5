package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.SolicitudAmistadService;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ControladorHome {

    private final ServicioLibro servicioLibro;
    private final ServicioSuscriptor servicioSuscriptor;
    private final ServicioPreferencias servicioPreferencias;
    private final SolicitudAmistadService solicitudAmistadService;
    private final NotificacionService notificacionService;
    private final ServicioSliderHero servicioSliderHero;
    private PerfilService perfilService;

    @Autowired
    public ControladorHome(ServicioLibro servicioLibro,PerfilService perfilService, ServicioSuscriptor servicioSuscriptor, ServicioPreferencias servicioPreferencias, SolicitudAmistadService solicitudAmistadService, NotificacionService notificacionService, ServicioSliderHero servicioSliderHero) {
        this.servicioLibro = servicioLibro;
        this.servicioSuscriptor = servicioSuscriptor;
        this.servicioPreferencias = servicioPreferencias;
        this.solicitudAmistadService = solicitudAmistadService;
        this.notificacionService = notificacionService;
        this.servicioSliderHero = servicioSliderHero;
        this.perfilService = perfilService;
    }

    @GetMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        if (usuario != null) {
            ModelAndView modelAndView = new ModelAndView("home");
            List<Libro> libros = servicioLibro.obtenerTodosLosLibros();
            List<Libro> librosOrdenados = servicioLibro.ordenarPorFechaAgregado();
            List<Libro> librosDestacados = servicioLibro.getLibrosDestacados();
            List<SliderHero> sliderHeroes = servicioSliderHero.getAllSliderHeroes();
            Set<Libro> librosUsuario = perfilService.buscarMisLibros(usuario);

            // Agrega información de si el usuario ya compró el libro
            Map<Long, Boolean> librosCompradosMap = new HashMap<>();
            for (Libro libro : libros) {
                librosCompradosMap.put(libro.getId(), librosUsuario.contains(libro));
            }

            Preferencias preferencias = new Preferencias(usuario);
            List<Libro> librosRecomendados = servicioPreferencias.recomendarLibros(preferencias);

            modelAndView.addObject("libros", libros);
            modelAndView.addObject("librosOrdenados", librosOrdenados);
            modelAndView.addObject("librosDestacados", librosDestacados);
            modelAndView.addObject("sliderHeroes", sliderHeroes);
            modelAndView.addObject("librosUsuario", librosUsuario);
            modelAndView.addObject("librosRecomendados", librosRecomendados);
            modelAndView.addObject("cantidadNotificaciones", notificacionService.cantidadDeNotificaciones(usuario));
            modelAndView.addObject("librosCompradosMap", librosCompradosMap);

            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/suscribirse")
    public String agregarSuscriptor(@RequestParam("email") String email, @RequestParam("nombre") String nombre) {
        servicioSuscriptor.agregarSuscriptor(email, nombre);
        return "redirect:/home";
    }

}