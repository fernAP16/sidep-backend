package com.sidep.proyect.backend.dto.out;

import java.util.Date;

public class DespachoRegisterOutDto {

    private Integer idDespacho;
    private Integer idPlanta;
    private Date fechaVencLicencia;
    private Date fechaVencCircTracto;
    private Date fechaVencCircCarreta;
    private Integer tieneTarjPropiedadTracto;
    private Integer tieneTarjPropiedadCarreta;
    private Date fechaVencSoatTracto;
    private String errorMessage;

    public DespachoRegisterOutDto() {
    }

    public Integer getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }

    public Integer getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }

    public Date getFechaVencLicencia() {
        return fechaVencLicencia;
    }

    public void setFechaVencLicencia(Date fechaVencLicencia) {
        this.fechaVencLicencia = fechaVencLicencia;
    }

    public Date getFechaVencCircTracto() {
        return fechaVencCircTracto;
    }

    public void setFechaVencCircTracto(Date fechaVencCircTracto) {
        this.fechaVencCircTracto = fechaVencCircTracto;
    }

    public Date getFechaVencCircCarreta() {
        return fechaVencCircCarreta;
    }

    public void setFechaVencCircCarreta(Date fechaVencCircCarreta) {
        this.fechaVencCircCarreta = fechaVencCircCarreta;
    }

    public Integer getTieneTarjPropiedadTracto() {
        return tieneTarjPropiedadTracto;
    }

    public void setTieneTarjPropiedadTracto(Integer tieneTarjPropiedadTracto) {
        this.tieneTarjPropiedadTracto = tieneTarjPropiedadTracto;
    }

    public Integer getTieneTarjPropiedadCarreta() {
        return tieneTarjPropiedadCarreta;
    }

    public void setTieneTarjPropiedadCarreta(Integer tieneTarjPropiedadCarreta) {
        this.tieneTarjPropiedadCarreta = tieneTarjPropiedadCarreta;
    }

    public Date getFechaVencSoatTracto() {
        return fechaVencSoatTracto;
    }

    public void setFechaVencSoatTracto(Date fechaVencSoatTracto) {
        this.fechaVencSoatTracto = fechaVencSoatTracto;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "DespachoRegisterOutDto [idDespacho=" + idDespacho + ", idPlanta=" + idPlanta + ", fechaVencLicencia="
                + fechaVencLicencia + ", fechaVencCircTracto=" + fechaVencCircTracto + ", fechaVencCircCarreta="
                + fechaVencCircCarreta + ", tieneTarjPropiedadTracto=" + tieneTarjPropiedadTracto
                + ", tieneTarjPropiedadCarreta=" + tieneTarjPropiedadCarreta + ", fechaVencSoatTracto="
                + fechaVencSoatTracto + ", errorMessage=" + errorMessage + "]";
    }    

}
