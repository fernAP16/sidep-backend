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
@Table(name = "sd_vehiculo")
public class Vehiculo {
    @Id
    @Column(name = "id_vehiculo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVehiculo;

    @JoinColumn(name = "id_tipo_vehiculo")
    @ManyToOne
    @NotNull
    private TipoVehiculo tipoVehiculo;

    @Column(name = "placa")
    @Size(max = 10, message = "placa: maximo 10 caracteres")
    @NotBlank
    private String placa;

    @Column(name = "largo")
    @NotNull
    private Double largo;

    @Column(name = "ancho")
    @NotNull
    private Double ancho;

    @Column(name = "altura")
    @NotNull
    private Double altura;

    @Column(name = "peso")
    @NotNull
    private Double peso;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_venc_circulacion")
    @NotNull
    private Date fechaVencCirculacion;

    @Column(name = "tiene_tarjeta_propiedad")
    @NotNull
    private Integer tieneTarjetaPropiedad;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_venc_soat")
    @NotNull
    private Date fechaVencSoat;

    @Embedded
    @Valid
    private Auditoria auditoria;
    
    public Vehiculo() {

    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFechaVencCirculacion() {
        return fechaVencCirculacion;
    }

    public void setFechaVencCirculacion(Date fechaVencCirculacion) {
        this.fechaVencCirculacion = fechaVencCirculacion;
    }

    public Integer getTieneTarjetaPropiedad() {
        return tieneTarjetaPropiedad;
    }

    public void setTieneTarjetaPropiedad(Integer tieneTarjetaPropiedad) {
        this.tieneTarjetaPropiedad = tieneTarjetaPropiedad;
    }

    public Date getFechaVencSoat() {
        return fechaVencSoat;
    }

    public void setFechaVencSoat(Date fechaVencSoat) {
        this.fechaVencSoat = fechaVencSoat;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Vehiculo [idVehiculo=" + idVehiculo + ", tipoVehiculo=" + tipoVehiculo + ", placa=" + placa + ", largo="
                + largo + ", ancho=" + ancho + ", altura=" + altura + ", peso=" + peso + ", fechaVencCirculacion="
                + fechaVencCirculacion + ", tieneTarjetaPropiedad=" + tieneTarjetaPropiedad + ", fechaVencSoat="
                + fechaVencSoat + ", auditoria=" + auditoria + "]";
    }

}
