package com.sidep.proyect.backend.dto.out;

public class ColaPesajeDatosOutDto {
    
    private Integer idColaPesaje;
    private Integer idZonaBalanza;
    private String codigoZonaBalanza;
    private Double valorPesaje;
    private Integer posicion;
    private Double valorVehiculo;
    private Double limiteInf;
    private Double limiteSup;
    
    public ColaPesajeDatosOutDto() {
    }

    
    public Integer getIdColaPesaje() {
        return idColaPesaje;
    }
    public void setIdColaPesaje(Integer idColaPesaje) {
        this.idColaPesaje = idColaPesaje;
    }
    public Integer getIdZonaBalanza() {
        return idZonaBalanza;
    }
    public void setIdZonaBalanza(Integer idZonaBalanza) {
        this.idZonaBalanza = idZonaBalanza;
    }
    public String getCodigoZonaBalanza() {
        return codigoZonaBalanza;
    }
    public void setCodigoZonaBalanza(String codigoZonaBalanza) {
        this.codigoZonaBalanza = codigoZonaBalanza;
    }
    public Double getValorPesaje() {
        return valorPesaje;
    }
    public void setValorPesaje(Double valorPesaje) {
        this.valorPesaje = valorPesaje;
    }
    public Integer getPosicion() {
        return posicion;
    }
    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }
    public Double getValorVehiculo() {
        return valorVehiculo;
    }
    public void setValorVehiculo(Double valorVehiculo) {
        this.valorVehiculo = valorVehiculo;
    }
    public Double getLimiteInf() {
        return limiteInf;
    }
    public void setLimiteInf(Double limiteInf) {
        this.limiteInf = limiteInf;
    }
    public Double getLimiteSup() {
        return limiteSup;
    }
    public void setLimiteSup(Double limiteSup) {
        this.limiteSup = limiteSup;
    }
    
    
}
