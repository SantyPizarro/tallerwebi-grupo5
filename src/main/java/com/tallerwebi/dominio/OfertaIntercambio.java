package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OfertaIntercambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id_solicitante")
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "libro_id_solicitante")
    private Libro libroSolicitante;

    @ManyToOne
    @JoinColumn(name = "usuario_id_solicitado")
    private Usuario solicitado;

    @ManyToOne
    @JoinColumn(name = "libro_id_solicitado")
    private Libro libroSolicitado;

    private Boolean aceptada = false;

    public Boolean getAceptada() {
        return aceptada;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibroSolicitado() {
        return libroSolicitado;
    }

    public void setLibroSolicitado(Libro libroSolicitado) {
        this.libroSolicitado = libroSolicitado;
    }

    public Libro getLibroSolicitante() {
        return libroSolicitante;
    }

    public void setLibroSolicitante(Libro libroSolicitante) {
        this.libroSolicitante = libroSolicitante;
    }

    public Usuario getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Usuario solicitado) {
        this.solicitado = solicitado;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }
}
