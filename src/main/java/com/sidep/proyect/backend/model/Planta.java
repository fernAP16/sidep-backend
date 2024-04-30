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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_planta")
public class Planta {
    @Id
    @Column(name = "id_planta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlanta;

    @Column(name = "nombre")
    @Size(max = 100, message = "nombre: maximo 100 caracteres")
    @NotBlank
    private String nombre;

    @Column(name = "direccion")
    @Size(max = 100, message = "direccion: maximo 100 caracteres")
    @NotBlank
    private String direccion;

    @Column(name = "distrito")
    @Size(max = 100, message = "distrito: maximo 100 caracteres")
    @NotBlank
    private String distrito;

    @Column(name = "capacidad_maxima")
    @NotNull
    private Integer capacidadMaxima;

    @Column(name = "ubicacion_x1")
    @NotNull
    private Double ubicacionX1;

    @Column(name = "ubicacion_x2")
    @NotNull
    private Double ubicacionX2;

    @Column(name = "ubicacion_y1")
    @NotNull
    private Double ubicacionY1;

    @Column(name = "ubicacion_y2")
    @NotNull
    private Double ubicacionY2;

    @Column(name = "limite_inf_pesaje_antes")
    @NotNull
    private Double limiteInfPesajeAntes;

    @Column(name = "limite_sup_pesaje_antes")
    @NotNull
    private Double limiteSupPesajeAntes;

    @Column(name = "limite_inf_pesaje_despues")
    @NotNull
    private Double limiteInfPesajeDespues;

    @Column(name = "limite_sup_pesaje_despues")
    @NotNull
    private Double limiteSupPesajeDespues;

    @Embedded
    @Valid
    private Auditoria auditoria; 
    
    public Planta() {
    }

    public Integer getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Double getUbicacionX1() {
        return ubicacionX1;
    }

    public void setUbicacionX1(Double ubicacionX1) {
        this.ubicacionX1 = ubicacionX1;
    }

    public Double getUbicacionX2() {
        return ubicacionX2;
    }

    public void setUbicacionX2(Double ubicacionX2) {
        this.ubicacionX2 = ubicacionX2;
    }

    public Double getUbicacionY1() {
        return ubicacionY1;
    }

    public void setUbicacionY1(Double ubicacionY1) {
        this.ubicacionY1 = ubicacionY1;
    }

    public Double getUbicacionY2() {
        return ubicacionY2;
    }

    public void setUbicacionY2(Double ubicacionY2) {
        this.ubicacionY2 = ubicacionY2;
    }

    public Double getLimiteInfPesajeAntes() {
        return limiteInfPesajeAntes;
    }

    public void setLimiteInfPesajeAntes(Double limiteInfPesajeAntes) {
        this.limiteInfPesajeAntes = limiteInfPesajeAntes;
    }

    public Double getLimiteSupPesajeAntes() {
        return limiteSupPesajeAntes;
    }

    public void setLimiteSupPesajeAntes(Double limiteSupPesajeAntes) {
        this.limiteSupPesajeAntes = limiteSupPesajeAntes;
    }

    public Double getLimiteInfPesajeDespues() {
        return limiteInfPesajeDespues;
    }

    public void setLimiteInfPesajeDespues(Double limiteInfPesajeDespues) {
        this.limiteInfPesajeDespues = limiteInfPesajeDespues;
    }

    public Double getLimiteSupPesajeDespues() {
        return limiteSupPesajeDespues;
    }

    public void setLimiteSupPesajeDespues(Double limiteSupPesajeDespues) {
        this.limiteSupPesajeDespues = limiteSupPesajeDespues;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Planta [idPlanta=" + idPlanta + ", nombre=" + nombre + ", direccion=" + direccion + ", distrito="
                + distrito + ", capacidadMaxima=" + capacidadMaxima + ", ubicacionX1=" + ubicacionX1 + ", ubicacionX2="
                + ubicacionX2 + ", ubicacionY1=" + ubicacionY1 + ", ubicacionY2=" + ubicacionY2
                + ", limiteInfPesajeAntes=" + limiteInfPesajeAntes + ", limiteSupPesajeAntes=" + limiteSupPesajeAntes
                + ", limiteInfPesajeDespues=" + limiteInfPesajeDespues + ", limiteSupPesajeDespues="
                + limiteSupPesajeDespues + ", auditoria=" + auditoria + "]";
    }

    

}
