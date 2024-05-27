package com.sidep.proyect.backend.dto.out;

import java.util.Date;

public class ColaCargaDespachoOutDto {
    private Integer idOrdenRecojo;
    private Integer idColaCanal;
    private Integer idCanalCarga;
    private String codigoCanalCarga;
    private String razonSocial;
    private String producto;
    private String cantidad;
    private Date horaInicio;
    private Date horaFin;
    
    public Integer getIdOrdenRecojo() {
        return idOrdenRecojo;
    }
    public void setIdOrdenRecojo(Integer idOrdenRecojo) {
        this.idOrdenRecojo = idOrdenRecojo;
    }
    public Integer getIdColaCanal() {
        return idColaCanal;
    }
    public void setIdColaCanal(Integer idColaCanal) {
        this.idColaCanal = idColaCanal;
    }
    public Integer getIdCanalCarga() {
        return idCanalCarga;
    }
    public void setIdCanalCarga(Integer idCanalCarga) {
        this.idCanalCarga = idCanalCarga;
    }
    public String getCodigoCanalCarga() {
        return codigoCanalCarga;
    }
    public void setCodigoCanalCarga(String codigoCanalCarga) {
        this.codigoCanalCarga = codigoCanalCarga;
    }
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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

    
}
