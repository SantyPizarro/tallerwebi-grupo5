package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.CompraLibroService;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorComprar {


    private CompraLibroService libroService;


    public ControladorComprar(CompraLibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/comprar")
    public ModelAndView comprar() {
        ModelMap model = new ModelMap();
        model.put("subtotal",9.99);

        return new ModelAndView("comprar",model);
    }

    @GetMapping("/comprar/{titulo}")
    public ModelAndView comprar(@PathVariable String titulo) {
        ModelMap model = new ModelMap();
        model.put("subtotal", libroService.sumarSubtotal(titulo));
        return new ModelAndView("comprar", model);
    }


    //ESTO ES UNA PRUEBA {BORRAR}
   /* @GetMapping("/comprar/{numero1}/{numero2}")
    public ModelAndView comprar(@PathVariable Integer numero1, @PathVariable Integer numero2) {
        ModelMap model = new ModelMap();
        model.put("subtotal", libroService.sumarNumeros(numero1, numero2));
        return new ModelAndView("comprar", model);
    }*/

}


