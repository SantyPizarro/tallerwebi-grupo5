package com.tallerwebi.dominio;

public interface RepositorioMailer  {

    void enviarMail(String para, String tema, String texto);
}
