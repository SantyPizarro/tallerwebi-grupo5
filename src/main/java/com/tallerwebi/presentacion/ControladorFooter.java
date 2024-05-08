package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorFooter {

    @GetMapping("/footer")
    public ModelAndView footer(){
        return new ModelAndView("footer");
    }
    @GetMapping("/header-busqueda")
    public ModelAndView headerBusqueda(){
        return new ModelAndView("header-busqueda");
    }
    @GetMapping("/header")
    public ModelAndView header(){
        return new ModelAndView("header");
    }
    @GetMapping("/comprar")
    public ModelAndView comprar(){return new ModelAndView("comprar");}

}
