package com.sidep.proyect.backend.dto.in;

public class DespachoNuevoPesajeInDto {
    private Integer idDespacho;
    private Double valorPesaje;
    private Integer tipoPesaje;
    private Integer idZonaBalanza;

    public DespachoNuevoPesajeInDto() {
    }
    
    public Integer getIdDespacho() {
        return idDespacho;
    }
    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }
    public Double getValorPesaje() {
        return valorPesaje;
    }
    public void setValorPesaje(Double valorPesaje) {
        this.valorPesaje = valorPesaje;
    }
    public Integer getTipoPesaje() {
        return tipoPesaje;
    }
    public void setTipoPesaje(Integer tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }
    public Integer getIdZonaBalanza() {
        return idZonaBalanza;
    }
    public void setIdZonaBalanza(Integer idZonaBalanza) {
        this.idZonaBalanza = idZonaBalanza;
    }

    
}
