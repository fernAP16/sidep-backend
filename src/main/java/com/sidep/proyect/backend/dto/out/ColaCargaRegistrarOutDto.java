package com.sidep.proyect.backend.dto.out;

public class ColaCargaRegistrarOutDto {
    private Integer idColaCargaNuevo;
    private Integer posicionAsignada;
    private String canalCargaAsignado;
    
    public ColaCargaRegistrarOutDto() {
    }

    public Integer getIdColaCargaNuevo() {
        return idColaCargaNuevo;
    }

    public void setIdColaCargaNuevo(Integer idColaCargaNuevo) {
        this.idColaCargaNuevo = idColaCargaNuevo;
    }

    public Integer getPosicionAsignada() {
        return posicionAsignada;
    }

    public void setPosicionAsignada(Integer posicionAsignada) {
        this.posicionAsignada = posicionAsignada;
    }

    public String getCanalCargaAsignado() {
        return canalCargaAsignado;
    }

    public void setCanalCargaAsignado(String canalCargaAsignado) {
        this.canalCargaAsignado = canalCargaAsignado;
    }

    
    
}
