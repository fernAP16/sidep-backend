package com.sidep.proyect.backend.dto.in;

public class TurnoRevisionInDto {
    
    private Integer idDespacho;
    private Double x;
    private Double y;

    public TurnoRevisionInDto() {
    }

    public Integer getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "TurnoRevisionInDto [idDespacho=" + idDespacho + ", x=" + x + ", y=" + y + "]";
    }

    
    
}
