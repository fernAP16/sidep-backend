package com.sidep.proyect.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_conductor")
public class Conductor {

    @Id
    @Column(name = "id_conductor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConductor;

    @JoinColumn(name = "id_usuario")
    @ManyToOne
    @NotNull
    private Usuario usuario;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_venc_licencia")
    @NotNull
    private Date fechaVencLicencia;

    @Column(name = "clave_digital")
    @Size(max = 4, message = "clave_digital: maximo 4 caracteres")
    @NotBlank
    private String claveDigital;

    @Embedded
    @Valid
    private Auditoria auditoria;

    
    public Conductor() {
    }

    
    public Integer getIdConductor() {
        return idConductor;
    }


    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Date getFechaVencLicencia() {
        return fechaVencLicencia;
    }


    public void setFechaVencLicencia(Date fechaVencLicencia) {
        this.fechaVencLicencia = fechaVencLicencia;
    }


    public String getClaveDigital() {
        return claveDigital;
    }


    public void setClaveDigital(String claveDigital) {
        this.claveDigital = claveDigital;
    }


    public Auditoria getAuditoria() {
        return auditoria;
    }


    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }


    @Override
    public String toString() {
        return "Conductor [idConductor=" + idConductor + ", fechaVencLicencia=" + fechaVencLicencia + ", claveDigital="
                + claveDigital + ", auditoria=" + auditoria + "]";
    }

    
    
}
