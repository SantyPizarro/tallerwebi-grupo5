package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.excepcion.CompraLibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorComprar {

    @Autowired
    private CompraLibroService libroService;

    private Double subtotal = 0.0;


    @GetMapping("/comprar")
    public ModelAndView comprar() {
        return new ModelAndView("comprar");
    }


    @RequestMapping("/comprar")
    public void comprarLibro(@RequestParam String titulo) {
        List<Libro> libros = libroService.obtenerLibros();
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                subtotal += libro.getPrecio();
                break;
            }
        }
    }

    @RequestMapping("/subtotal")
    public Double obtenerSubtotal() {
        return subtotal;
    }

}
