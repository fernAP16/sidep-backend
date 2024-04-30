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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sd_turno_incidencia")
public class TurnoIncidencia {
    @Id
    @Column(name = "id_turno_incidencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurnoIncidencia;

    @JoinColumn(name = "id_turno_revision")
    @ManyToOne
    @NotNull
    private TurnoRevision turnoRevision;

    @JoinColumn(name = "id_incidencia")
    @ManyToOne
    @NotNull
    private Incidencia incidencia;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public TurnoIncidencia() {
    }

    public Integer getIdTurnoIncidencia() {
        return idTurnoIncidencia;
    }

    public void setIdTurnoIncidencia(Integer idTurnoIncidencia) {
        this.idTurnoIncidencia = idTurnoIncidencia;
    }

    public TurnoRevision getTurnoRevision() {
        return turnoRevision;
    }

    public void setTurnoRevision(TurnoRevision turnoRevision) {
        this.turnoRevision = turnoRevision;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "TurnoIncidencia [idTurnoIncidencia=" + idTurnoIncidencia + ", turnoRevision=" + turnoRevision
                + ", incidencia=" + incidencia + ", auditoria=" + auditoria + "]";
    }
    
    
    
}
