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
@Table(name = "sd_canal_carga")
public class CanalCarga {
    @Id
    @Column(name = "id_canal_carga")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCanalCarga;

    @JoinColumn(name = "id_planta")
    @ManyToOne
    @NotNull
    private Planta planta;

    @Column(name = "codigo")
    @NotBlank
    @Size(max = 10, message = "codigo: maximo 10 caracteres")
    private String codigo;

    @Column(name = "qr_fisico")
    @Size(max = 50, message = "qr_fisico: maximo 50 caracteres")
    @NotBlank
    private String qrFisico;
    
    @Embedded
    @Valid
    private Auditoria auditoria;

    public CanalCarga() {
    }

    public Integer getIdCanalCarga() {
        return idCanalCarga;
    }
    public void setIdCanalCarga(Integer idCanalCarga) {
        this.idCanalCarga = idCanalCarga;
    }
    public Planta getPlanta() {
        return planta;
    }
    public void setPlanta(Planta planta) {
        this.planta = planta;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getQrFisico() {
        return qrFisico;
    }
    public void setQrFisico(String qrFisico) {
        this.qrFisico = qrFisico;
    }
    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    
}
