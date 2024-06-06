package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioMailer;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioMailerImpl implements RepositorioMailer {

    @Autowired
    private Session mailSession;

    public void enviarMail(String para, String tema, String texto) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("tu_correo@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
            message.setSubject(tema);
            message.setText(texto);

            Transport.send(message);
        } catch (MessagingException MailInexistenteException) {
            MailInexistenteException.printStackTrace();
        }
    }

}
