package com.tallerwebi.dominio;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioMailer {

    private final RepositorioMailer repositorioMailer;

    @Autowired
    public ServicioMailer(RepositorioMailer repositorioMailer) {
        this.repositorioMailer = repositorioMailer;
    }

    public void enviarMail(String para, String tema, String texto){
        repositorioMailer.enviarMail(para, tema, texto);
    }

}