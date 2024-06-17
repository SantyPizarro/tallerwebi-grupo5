package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCorreo;
import com.tallerwebi.dominio.ServicioSuscriptor;
import com.tallerwebi.dominio.Suscriptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorNewsletter {

    private final ServicioCorreo servicioCorreo;
    private final ServicioSuscriptor servicioSuscriptor;

    @Autowired
    public ControladorNewsletter(ServicioCorreo servicioCorreo, ServicioSuscriptor servicioSuscriptor) {
        this.servicioCorreo = servicioCorreo;
        this.servicioSuscriptor = servicioSuscriptor;
    }

    @PostMapping("/enviarNewsletter")
    public ModelAndView enviarNewsletter(@RequestParam("tema") String tema, @RequestParam("mensaje") String mensaje) {
        List<Suscriptor> suscriptores = servicioSuscriptor.obtenerTodosLosSuscriptores();


        for (Suscriptor suscriptor : suscriptores) {
            String mensajePersonalizado = "Hola " + suscriptor.getName() + ", " + mensaje;
            servicioCorreo.enviarCorreo(suscriptor.getEmail(), tema, mensajePersonalizado);
        }

        return new ModelAndView("redirect:/home");
    }
}