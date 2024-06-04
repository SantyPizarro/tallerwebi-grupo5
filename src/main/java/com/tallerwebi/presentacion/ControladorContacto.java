package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MensajeContacto;
import com.tallerwebi.dominio.RepositorioMensajeContacto;
import com.tallerwebi.dominio.ServicioMensajeContacto;
import com.tallerwebi.infraestructura.RepositorioMensajeContactoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorContacto {

    private final ServicioMensajeContacto servicioMensajeContacto;

    @Autowired
    public ControladorContacto(ServicioMensajeContacto servicioMensajeContacto){
        this.servicioMensajeContacto = servicioMensajeContacto;
    }

    @GetMapping("/contacto")
    public ModelAndView mostrarContacto(){
        ModelAndView modelAndView = new ModelAndView("contacto");
        return modelAndView;


    }


    @PostMapping("/enviarMensajeContacto")
    public ModelAndView enviarMensajeContacto(@RequestParam("nombre") String nombre,
                                        @RequestParam("email") String email,
                                        @RequestParam("telefono") String telefono,
                                        @RequestParam("mensaje") String mensaje) {
        servicioMensajeContacto.agregarMensaje(nombre, email, telefono, mensaje);
        return new ModelAndView("contacto");

    }

    public ModelAndView planes() {
        return new ModelAndView("planes");
    }


    @GetMapping("/contactoMensajes")
    public String mostrarMensajes(Model model) {
        model.addAttribute("mensajes", servicioMensajeContacto.obtenerMensajes());
        return "contacto-mensaje";
    }



}