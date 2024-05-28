package com.sidep.proyect.backend.dto.in;

public class SalidaDespachoInDto {
    private Integer idPlanta;
    private String qrSalida;
    private Integer idDespacho;

    public SalidaDespachoInDto() {
    }
    
    public Integer getIdPlanta() {
        return idPlanta;
    }
    public void setIdPlanta(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }
    public String getQrSalida() {
        return qrSalida;
    }
    public void setQrSalida(String qrSalida) {
        this.qrSalida = qrSalida;
    }
    public Integer getIdDespacho() {
        return idDespacho;
    }
    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }
    
}
