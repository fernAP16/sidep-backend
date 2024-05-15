package com.sidep.proyect.backend.dto.in;

public class DespachoRegisterInDto {
    
    private Integer idOrden;
    private Double x;
    private Double y;
    
    public DespachoRegisterInDto() {
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
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
        return "DespachoRegisterInDto [idOrden=" + idOrden + ", x=" + x + ", y=" + y + "]";
    }
    
    

}
