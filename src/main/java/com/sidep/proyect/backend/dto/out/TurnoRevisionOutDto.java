package com.sidep.proyect.backend.dto.out;

public class TurnoRevisionOutDto {
    private Integer idTurnoRevision;
    private Integer turnoAsignado;
    private Integer turnoActual;

    public TurnoRevisionOutDto() {
    }

    public Integer getIdTurnoRevision() {
        return idTurnoRevision;
    }

    public void setIdTurnoRevision(Integer idTurnoRevision) {
        this.idTurnoRevision = idTurnoRevision;
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

    @Override
    public String toString() {
        return "TurnoRevisionOutDto [idTurnoRevision=" + idTurnoRevision + ", turnoAsignado=" + turnoAsignado
                + ", turnoActual=" + turnoActual + "]";
    }
    
    
    
}
