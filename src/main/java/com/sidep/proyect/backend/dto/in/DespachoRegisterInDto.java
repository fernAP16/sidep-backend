package com.sidep.proyect.backend.dto.in;

public class DespachoRegisterInDto {
    
    private Integer idOrden;

    public DespachoRegisterInDto() {
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    @Override
    public String toString() {
        return "DespachoRegisterInDto [idOrden=" + idOrden + "]";
    }

    
}
