package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_cliente")
public class Cliente {

    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(name = "razon_social")
    @Size(max = 100, message = "razon_social: maximo 100 caracteres")
    @NotBlank
    private String razonSocial;

    @Column(name = "ruc")
    @Size(max = 11, message = "ruc: maximo 11 caracteres")
    @NotBlank
    private String ruc;

    @Embedded
    @Valid
    private Auditoria auditoria;

    
    public Cliente() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }


    public String getRazonSocial() {
        return razonSocial;
    }


    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }


    public String getRuc() {
        return ruc;
    }


    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", razonSocial=" + razonSocial + ", ruc=" + ruc + "]";
    }

    
}
