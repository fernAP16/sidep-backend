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
@Table(name = "sd_cola_canal")
public class ColaCanal {

    @Id
    @Column(name = "id_cola_canal")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idColaCanal;

    @JoinColumn(name = "id_despacho")
    @ManyToOne
    @NotNull
    private Despacho despacho;

    @JoinColumn(name = "id_canal_carga")
    @ManyToOne
    @NotNull
    private CanalCarga canalCarga;

    @Column(name = "posicion")
    @NotNull
    private Integer posicion;

    @Embedded
    @Valid
    private Auditoria auditoria;
    
    public ColaCanal() {
    }

    public Integer getIdColaCanal() {
        return idColaCanal;
    }

    public void setIdColaCanal(Integer idColaCanal) {
        this.idColaCanal = idColaCanal;
    }

    public Despacho getDespacho() {
        return despacho;
    }

    public void setDespacho(Despacho despacho) {
        this.despacho = despacho;
    }

    public CanalCarga getCanalCarga() {
        return canalCarga;
    }

    public void setCanalCarga(CanalCarga canalCarga) {
        this.canalCarga = canalCarga;
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
        return "ColaCanal [idColaCanal=" + idColaCanal + ", despacho=" + despacho + ", canalCarga=" + canalCarga
                + ", posicion=" + posicion + ", auditoria=" + auditoria + "]";
    }

    

    

}
