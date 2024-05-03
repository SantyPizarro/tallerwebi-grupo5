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
}
