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
@Table(name = "sd_cola_pesaje")
public class ColaPesaje {
    @Id
    @Column(name = "id_cola_pesaje")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idColaPesaje;

    @JoinColumn(name = "id_despacho")
    @ManyToOne
    @NotNull
    private Despacho despacho;

    @JoinColumn(name = "id_zona_balanza")
    @ManyToOne
    @NotNull
    private ZonaBalanza zonaBalanza;

    @JoinColumn(name = "id_tipo_pesaje")
    @ManyToOne
    @NotNull
    private TipoPesaje tipoPesaje;

    @Column(name = "posicion")
    @NotNull
    private Integer posicion;

    @Embedded
    @Valid
    private Auditoria auditoria;
    
    public ColaPesaje() {
    }

    public Integer getIdColaPesaje() {
        return idColaPesaje;
    }

    public void setIdColaPesaje(Integer idColaPesaje) {
        this.idColaPesaje = idColaPesaje;
    }

    public Despacho getDespacho() {
        return despacho;
    }

    public void setDespacho(Despacho despacho) {
        this.despacho = despacho;
    }

    public ZonaBalanza getZonaBalanza() {
        return zonaBalanza;
    }

    public void setZonaBalanza(ZonaBalanza zonaBalanza) {
        this.zonaBalanza = zonaBalanza;
    }

    public TipoPesaje getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(TipoPesaje tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "ColaPesaje [idColaPesaje=" + idColaPesaje + ", despacho=" + despacho + ", zonaBalanza=" + zonaBalanza
                + ", tipoPesaje=" + tipoPesaje + ", posicion=" + posicion + ", auditoria=" + auditoria + "]";
    }

    
    
    

}
