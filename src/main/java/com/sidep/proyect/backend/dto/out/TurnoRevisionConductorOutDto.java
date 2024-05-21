package com.sidep.proyect.backend.dto.out;

import java.util.Date;

public class TurnoRevisionConductorOutDto {
    private Integer idTurnoRevision;
    private String codigoPuntoControl;
    private Integer esAprobado;
    private Date horaInicio;
    private Date horaFin;
    
    public TurnoRevisionConductorOutDto() {
    }

    public Integer getIdTurnoRevision() {
        return idTurnoRevision;
    }

    public void setIdTurnoRevision(Integer idTurnoRevision) {
        this.idTurnoRevision = idTurnoRevision;
    }

    public String getCodigoPuntoControl() {
        return codigoPuntoControl;
    }

    public void setCodigoPuntoControl(String codigoPuntoControl) {
        this.codigoPuntoControl = codigoPuntoControl;
    }

    public Integer getEsAprobado() {
        return esAprobado;
    }

    public void setEsAprobado(Integer esAprobado) {
        this.esAprobado = esAprobado;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
    
}
