package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;


    @Entity
    public class SolicitudAmistad implements NotificacionService{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "usuario_id_solicitante")
        private Usuario solicitante;

        @ManyToOne
        @JoinColumn(name = "usuario_id_solicitado")
        private Usuario solicitado;

        private LocalDateTime fechaSolicitud;

        private Boolean aceptada = false;

        private String tipo = "amistad";
        // Getters y setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setSolicitante(Usuario solicitante) {
            this.solicitante = solicitante;
        }

        public Usuario getSolicitado() {
            return solicitado;
        }

        public void setSolicitado(Usuario solicitado) {
            this.solicitado = solicitado;
        }


        public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
            this.fechaSolicitud = fechaSolicitud;
        }

        public Boolean getAceptada() {
            return aceptada;
        }

        public void setAceptada(Boolean aceptada) {
            this.aceptada = aceptada;
        }


        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public String getTipo() {
            return tipo;
        }

        @Override
        public Usuario getSolicitante() {
            return solicitante;
        }

        @Override
        public LocalDateTime getFecha() {
            return fechaSolicitud;
        }
    }
