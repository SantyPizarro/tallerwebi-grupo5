package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioBarraBusqueda;
import com.tallerwebi.dominio.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorBuscar {

    private final ServicioBarraBusqueda servicioBarraBusqueda;
    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorBuscar(ServicioBarraBusqueda servicioBarraBusqueda, ServicioLibro servicioLibro) {
        this.servicioBarraBusqueda = servicioBarraBusqueda;
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("buscar-libro")
    public ModelAndView buscarLibro(@RequestParam("titulo") String titulo) {
        ModelAndView modelAndView = new ModelAndView("libros-buscar");
        List<Libro> librosEncontrados = servicioBarraBusqueda.buscarPorTitulo(titulo);
        List<String> generos = servicioLibro.obtenerGeneros();
        List<String> editoriales = servicioLibro.obtenerEditoriales();

        modelAndView.addObject("librosEncontrados", librosEncontrados);
        modelAndView.addObject("generos", generos);
        modelAndView.addObject("editoriales", editoriales);

        return modelAndView;
    }
}