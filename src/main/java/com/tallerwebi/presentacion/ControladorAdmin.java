package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLibro;
import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioLibro;
import com.tallerwebi.dominio.excepcion.LibroExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorAdmin {

    private final ServicioLibro servicioLibro;

    @Autowired
    public ControladorAdmin(ServicioLibro servicioLibro) {
        this.servicioLibro = servicioLibro;
    }

    @GetMapping("/perfilAdmin")
    public ModelAndView perfilAdmin() {
        ModelMap model = new ModelMap();
        DatosLibro datosLibro = new DatosLibro();
        model.put("datosLibro", datosLibro);
        model.put("libros", servicioLibro.obtenerTodosLosLibros());
        return new ModelAndView("perfil-admin", model);
    }

    @RequestMapping(path = "/agregarLibroABDD", method = RequestMethod.POST)
    public ModelAndView agregarLibroABDD(@ModelAttribute("datosLibro") DatosLibro datosLibro,
                                        @RequestParam(name= "file", required = false) MultipartFile foto) throws LibroExistente {

        servicioLibro.agregarLibro(datosLibro, foto);

        return new ModelAndView ("redirect:/perfilAdmin");
    }

    @RequestMapping(path = "/eliminarLibroDeBDD", method = RequestMethod.POST)
    public ModelAndView eliminarLibroDeBDD(@ModelAttribute("titulo") String titulo){

        servicioLibro.eliminarLibro(titulo);

        return new ModelAndView ("redirect:/perfilAdmin");
    }

}
