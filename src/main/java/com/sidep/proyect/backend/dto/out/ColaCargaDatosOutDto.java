package com.sidep.proyect.backend.dto.out;

public class ColaCargaDatosOutDto {
    private Integer idColaCanal;
    private Integer idCanalCarga;
    private String codigoCanalCarga;
    private Integer posicionActual;
    
    public ColaCargaDatosOutDto() {
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
    public Integer getPosicionActual() {
        return posicionActual;
    }
    public void setPosicionActual(Integer posicionActual) {
        this.posicionActual = posicionActual;
    }

}
