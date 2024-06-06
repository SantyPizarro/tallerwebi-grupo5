package com.tallerwebi.dominio;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailerConfig {

    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.username", "tu_correo@gmail.com");
        props.put("mail.smtp.password", "tu_contraseña");

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(
                        "tu_correo@gmail.com", "tu_contraseña");
            }
        });
    }

    @Bean
    public jakarta.mail.Transport mailTransport(Session session) throws jakarta.mail.MessagingException {
        return session.getTransport("smtp");
    }
}