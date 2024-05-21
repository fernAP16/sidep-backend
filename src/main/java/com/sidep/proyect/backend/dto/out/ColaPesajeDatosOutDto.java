package com.sidep.proyect.backend.dto.out;

public class ColaPesajeDatosOutDto {
    
    private Integer idColaPesaje;
    private Integer idZonaBalanza;
    private String codigoZonaBalanza;
    private Double valorPesaje;
    
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
    
}
