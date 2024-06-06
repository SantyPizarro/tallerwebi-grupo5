package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMailer;
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

import java.util.List;

@Controller
public class ControladorNewsletter {

    @Autowired
    private ServicioMailer ServicioMailer;

    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping("/newsletter")
    public String MostrarNewsletter(Model model) {
        return "newsletter";
    }

    @PostMapping("/enviarNewsletter")
    public String sendNewsletter(@RequestParam("tema") String tema,
                                 @RequestParam("mensaje") String mensaje,
                                 Model model) {
        try {
            enviarNewsletter(tema, mensaje, model);
        } catch (Exception e) {
            model.addAttribute("error", "Error al enviar el newsletter: " + e.getMessage());
            return "newsletter";
        }
        model.addAttribute("success", "Newsletter enviado con éxito.");
        return "newsletter";
    }

    private void enviarNewsletter(String tema, String mensaje, Model model) {
        try (Session session = sessionFactory.openSession()) {
            Query<Suscriptor> query = session.createQuery("from Suscriptor", Suscriptor.class);
            List<Suscriptor> suscriptores = query.list();
            for (Suscriptor suscriptor : suscriptores) {
                ServicioMailer.enviarMail(suscriptor.getEmail(), tema, mensaje);
            }
        }
    }
}