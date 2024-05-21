package com.sidep.proyect.backend.dto.in;

public class OrdenActualizarPorDespachoInDto {
    private Integer idDespacho;
    private Integer idNuevoEstado;
    public Integer getIdDespacho() {
        return idDespacho;
    }
    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }
    public Integer getIdNuevoEstado() {
        return idNuevoEstado;
    }
    public void setIdNuevoEstado(Integer idNuevoEstado) {
        this.idNuevoEstado = idNuevoEstado;
    }

    
}
