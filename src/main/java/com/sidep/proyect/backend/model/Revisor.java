package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_revisor")
public class Revisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRevisor;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    @NotNull
    private Usuario usuario;
    
    @Column(name = "contrasena")
    @Size(max = 100, message = "contrasena: maximo 8 caracteres")
    @NotBlank
    private String contrasena;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public Revisor() {
    }


    public Integer getIdRevisor() {
        return idRevisor;
    }

    public void setIdRevisor(Integer idRevisor) {
        this.idRevisor = idRevisor;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public String toString() {
        return "Revisor [idRevisor=" + idRevisor + ", contrasena=" + contrasena + ", auditoria=" + auditoria + "]";
    }

}
