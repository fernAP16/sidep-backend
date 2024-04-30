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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sd_turno_revision")
public class TurnoRevision {
    @Id
    @Column(name = "id_turno_revision")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurnoRevision;

    @JoinColumn(name = "id_revisor")
    @ManyToOne
    @NotNull
    private Revisor revisor;

    @JoinColumn(name = "id_punto_control")
    @ManyToOne
    @NotNull
    private PuntoControl puntoControl;
    
    @Column(name = "turno_dia")
    @NotNull
    private Integer turnoDia;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "hora_inicio")
    @NotNull
    private Date horaInicio;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "hora_fin")
    @NotNull
    private Date horaFin;

    @Column(name = "es_aprobado")
    @NotNull
    private Integer esAprobado;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public TurnoRevision() {
    }

    public Integer getIdTurnoRevision() {
        return idTurnoRevision;
    }

    public void setIdTurnoRevision(Integer idTurnoRevision) {
        this.idTurnoRevision = idTurnoRevision;
    }

    public Revisor getRevisor() {
        return revisor;
    }

    public void setRevisor(Revisor revisor) {
        this.revisor = revisor;
    }

    public PuntoControl getPuntoControl() {
        return puntoControl;
    }

    public void setPuntoControl(PuntoControl puntoControl) {
        this.puntoControl = puntoControl;
    }

    public Integer getTurnoDia() {
        return turnoDia;
    }

    public void setTurnoDia(Integer turnoDia) {
        this.turnoDia = turnoDia;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getEsAprobado() {
        return esAprobado;
    }

    public void setEsAprobado(Integer esAprobado) {
        this.esAprobado = esAprobado;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "TurnoRevision [idTurnoRevision=" + idTurnoRevision + ", revisor=" + revisor + ", puntoControl="
                + puntoControl + ", turnoDia=" + turnoDia + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
                + ", esAprobado=" + esAprobado + ", auditoria=" + auditoria + "]";
    }
    
}
