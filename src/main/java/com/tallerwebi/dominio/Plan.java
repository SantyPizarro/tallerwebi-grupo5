package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_plan_id", nullable = false)
    private TipoPlan tipoPlan;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private Set<Usuario> usuarios;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public TipoPlan getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(TipoPlan tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
