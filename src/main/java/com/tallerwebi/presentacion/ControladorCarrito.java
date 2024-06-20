package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.LibroNoAgregado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class ControladorCarrito {


    private final CarritoService carritoService;
    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorCarrito(CarritoService carritoService, ServicioLibro servicioLibro) {
        this.carritoService = carritoService;
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("/mostrar-carrito")
    public ModelAndView mostrarLibrosComprados(HttpServletRequest request){
        HttpSession sesion = request.getSession();
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");
        Usuario usuario = (Usuario) sesion.getAttribute("USUARIO");
        Integer cantidadDelibros = (Integer) sesion.getAttribute("cantidadLibros");

        if(carrito != null){
            Set<Libro> librosComprados = carritoService.obtenerLibrosComprados(carrito);

            ModelMap modelo = new ModelMap();
            modelo.addAttribute("librosComprados", librosComprados);
            modelo.addAttribute("subtotal", carritoService.obtenerSubtotal(carrito));
            modelo.addAttribute("usuario", usuario);
            return new ModelAndView ("comprar", modelo);
        }

        return new ModelAndView ("redirect:/login");
    }

    @PostMapping("/carrito")
    public ModelAndView guardarLibros(@RequestParam("id") Long id, HttpServletRequest request) {

        HttpSession sesion = request.getSession();
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");
        Integer cantidadDelibros = (Integer) sesion.getAttribute("cantidadLibros");

        if(carrito == null){
            carrito = new Carrito();
            sesion.setAttribute("CARRITO", carrito);
            cantidadDelibros = 0;
        }



        try {
            carritoService.agregarLibrosAlCarrito(id, carrito);
            cantidadDelibros = (cantidadDelibros == null) ? 1 : cantidadDelibros + 1;
            sesion.setAttribute("cantidadLibros", cantidadDelibros);
        } catch (LibroNoAgregado e) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("error", "No se pudo agregar el libro al carrito. Por favor, inténtelo de nuevo más tarde.");
            return modelAndView;
        }
        return new ModelAndView("redirect:/home");


    }

    @PostMapping("/eliminarLibroDeCarrito")
    public ModelAndView eliminarLibroDeCarrito(@RequestParam("titulo") String titulo, HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");
        Integer cantidadDelibros = (Integer) sesion.getAttribute("cantidadLibros");

        if(carrito != null){
            carritoService.eliminarLibroDeCarrito(servicioLibro.buscarLibroPorTitulo(titulo), carrito);
            cantidadDelibros = (cantidadDelibros == null) ? 0 : cantidadDelibros - 1;
            sesion.setAttribute("cantidadLibros", cantidadDelibros);
        }

        return new ModelAndView("redirect:/mostrar-carrito");
    }
}
