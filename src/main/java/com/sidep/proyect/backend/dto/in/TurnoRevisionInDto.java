package com.sidep.proyect.backend.dto.in;

public class TurnoRevisionInDto {
    
    private Integer idDespacho;
    private Integer idPlanta;

    public TurnoRevisionInDto() {
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

    @Override
    public String toString() {
        return "TurnoRevisionInDto [idDespacho=" + idDespacho + ", idPlanta=" + idPlanta + "]";
    }   
    
}
