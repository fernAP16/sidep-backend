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
@Table(name = "sd_punto_control")
public class PuntoControl {
    @Id
    @Column(name = "id_punto_control")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPuntoControl;

    @JoinColumn(name = "id_planta")
    @ManyToOne
    @NotNull
    private Planta planta;

    @Column(name = "codigo")
    @Size(max = 10, message = "codigo: maximo 10 caracteres")
    @NotBlank
    private String codigo;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public PuntoControl() {

    }

    public Integer getIdPuntoControl() {
        return idPuntoControl;
    }

    public void setIdPuntoControl(Integer idPuntoControl) {
        this.idPuntoControl = idPuntoControl;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        return "PuntoControl [idPuntoControl=" + idPuntoControl + ", planta=" + planta + ", codigo=" + codigo
                + ", auditoria=" + auditoria + "]";
    }

     
    
}
