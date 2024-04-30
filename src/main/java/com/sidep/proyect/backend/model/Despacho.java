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
@Table(name = "sd_despacho")
public class Despacho {
    @Id
    @Column(name = "id_despacho")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDespacho;

    @JoinColumn(name = "id_planta")
    @ManyToOne
    @NotNull
    private Planta planta;

    @JoinColumn(name = "id_orden_recojo")
    @ManyToOne
    @NotNull
    private OrdenRecojo ordenRecojo;

    @JoinColumn(name = "id_estado_despacho")
    @ManyToOne
    @NotNull
    private EstadoDespacho estadoDespacho;

    @JoinColumn(name = "id_turno_revision")
    @ManyToOne
    @NotNull
    private TurnoRevision turnoRevision;
   
    @Column(name = "valor_pesaje_antes")
    @NotNull
    private Double valorPesajeAntes;

    @Column(name = "valor_pesaje_despues")
    @NotNull
    private Double valorPesajeDespues;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "hora_inicio_carga")
    @NotNull
    private Date horaInicioCarga;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "hora_fin_carga")
    @NotNull
    private Date horaFinCarga;

    @Embedded
    @Valid
    private Auditoria auditoria;    
    
    public Despacho() {
    }

    public Integer getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }

    public OrdenRecojo getOrdenRecojo() {
        return ordenRecojo;
    }

    public void setOrdenRecojo(OrdenRecojo ordenRecojo) {
        this.ordenRecojo = ordenRecojo;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public TurnoRevision getTurnoRevision() {
        return turnoRevision;
    }

    public void setTurnoRevision(TurnoRevision turnoRevision) {
        this.turnoRevision = turnoRevision;
    }

    public EstadoDespacho getEstadoDespacho() {
        return estadoDespacho;
    }

    public void setEstadoDespacho(EstadoDespacho estadoDespacho) {
        this.estadoDespacho = estadoDespacho;
    }

    public Double getValorPesajeAntes() {
        return valorPesajeAntes;
    }

    public void setValorPesajeAntes(Double valorPesajeAntes) {
        this.valorPesajeAntes = valorPesajeAntes;
    }

    public Double getValorPesajeDespues() {
        return valorPesajeDespues;
    }

    public void setValorPesajeDespues(Double valorPesajeDespues) {
        this.valorPesajeDespues = valorPesajeDespues;
    }

    public Date getHoraInicioCarga() {
        return horaInicioCarga;
    }

    public void setHoraInicioCarga(Date horaInicioCarga) {
        this.horaInicioCarga = horaInicioCarga;
    }

    public Date getHoraFinCarga() {
        return horaFinCarga;
    }

    public void setHoraFinCarga(Date horaFinCarga) {
        this.horaFinCarga = horaFinCarga;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Despacho [idDespacho=" + idDespacho + ", ordenRecojo=" + ordenRecojo + ", planta=" + planta
                + ", turnoRevision=" + turnoRevision + ", estadoDespacho=" + estadoDespacho + ", valorPesajeAntes="
                + valorPesajeAntes + ", valorPesajeDespues=" + valorPesajeDespues + ", horaInicioCarga="
                + horaInicioCarga + ", horaFinCarga=" + horaFinCarga + ", auditoria=" + auditoria + "]";
    }

        
    
}
