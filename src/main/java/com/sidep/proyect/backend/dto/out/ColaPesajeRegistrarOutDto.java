package com.sidep.proyect.backend.dto.out;

public class ColaPesajeRegistrarOutDto {
    private Integer idColaBalanzaNuevo;
    private Integer turnoAsignado;
    private String balanzaAsignada;

    public ColaPesajeRegistrarOutDto() {
    }
    
    public Integer getIdColaBalanzaNuevo() {
        return idColaBalanzaNuevo;
    }
    public void setIdColaBalanzaNuevo(Integer idColaBalanzaNuevo) {
        this.idColaBalanzaNuevo = idColaBalanzaNuevo;
    }
    public Integer getTurnoAsignado() {
        return turnoAsignado;
    }
    public void setTurnoAsignado(Integer turnoAsignado) {
        this.turnoAsignado = turnoAsignado;
    }
    public String getBalanzaAsignada() {
        return balanzaAsignada;
    }
    public void setBalanzaAsignada(String balanzaAsignada) {
        this.balanzaAsignada = balanzaAsignada;
    }

}
