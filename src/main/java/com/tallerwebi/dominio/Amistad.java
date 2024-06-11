package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Amistad{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private Usuario amigo;

    public Amistad(Usuario usuario, Usuario amigo) {
        this.amigo = amigo;
        this.usuario = usuario;
    }

    public Amistad() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
