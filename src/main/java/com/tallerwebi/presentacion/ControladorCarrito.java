package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.CarritoService;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioLibro;
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

@Controller
public class ControladorCarrito {


    private final CarritoService carritoService;

    @Autowired
    public ControladorCarrito(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/mostrar-carrito")
    public ModelAndView mostrarLibrosComprados(HttpServletRequest request){
        HttpSession sesion = request.getSession();
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");

        List<Libro> librosComprados = carritoService.obtenerLibrosComprados(carrito);
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("librosComprados", librosComprados);
        return new ModelAndView ("comprar", modelo);
    }

    @PostMapping("/carrito")
    public ModelAndView guardarLibros(@RequestParam("id") Long id, HttpServletRequest request) {

        HttpSession sesion = request.getSession();
        Carrito carrito = (Carrito) sesion.getAttribute("CARRITO");

        if(carrito == null){
            carrito = new Carrito();
            sesion.setAttribute("CARRITO", carrito);
        }

        try {
            carritoService.agregarLibrosAlCarrito(id, carrito);
        } catch (LibroNoAgregado e) {
            ModelAndView modelAndView = new ModelAndView("home");
            modelAndView.addObject("error", "No se pudo agregar el libro al carrito. Por favor, inténtelo de nuevo más tarde.");
            return modelAndView;
        }
        return new ModelAndView("redirect:/home");
    }

}
