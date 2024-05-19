package com.sidep.proyect.backend.dto.out;

public class ColaPesajeVacioOutDto {
    private Integer idColaBalanzaVacio;
    private Integer turnoAsignado;
    private Integer turnoActual;

    public ColaPesajeVacioOutDto() {
    }
    
    public Integer getIdColaBalanzaVacio() {
        return idColaBalanzaVacio;
    }
    public void setIdColaBalanzaVacio(Integer idColaBalanzaVacio) {
        this.idColaBalanzaVacio = idColaBalanzaVacio;
    }
    public Integer getTurnoAsignado() {
        return turnoAsignado;
    }
    public void setTurnoAsignado(Integer turnoAsignado) {
        this.turnoAsignado = turnoAsignado;
    }
    public Integer getTurnoActual() {
        return turnoActual;
    }
    public void setTurnoActual(Integer turnoActual) {
        this.turnoActual = turnoActual;
    }

    

}
