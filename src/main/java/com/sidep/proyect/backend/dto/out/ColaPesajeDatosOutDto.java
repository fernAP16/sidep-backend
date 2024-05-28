package com.sidep.proyect.backend.dto.out;

public class ColaPesajeDatosOutDto {
    
    private Integer idColaPesaje;
    private Integer idZonaBalanza;
    private String codigoZonaBalanza;
    private Double valorPesajeVacio;
    private Double valorPesajeLleno;
    private Integer posicion;
    private Double valorVehiculo;
    private Double limiteInf;
    private Double limiteSup;
    private Integer cantidadProductos;
    private Double pesoUnitario;
    
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
    public Double getValorPesajeVacio() {
        return valorPesajeVacio;
    }
    public void setValorPesajeVacio(Double valorPesajeVacio) {
        this.valorPesajeVacio = valorPesajeVacio;
    }
    public Double getValorPesajeLleno() {
        return valorPesajeLleno;
    }
    public void setValorPesajeLleno(Double valorPesajeLleno) {
        this.valorPesajeLleno = valorPesajeLleno;
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
    public Integer getCantidadProductos() {
        return cantidadProductos;
    }
    public void setCantidadProductos(Integer cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }
    public Double getPesoUnitario() {
        return pesoUnitario;
    }
    public void setPesoUnitario(Double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }

    
    
    
}
