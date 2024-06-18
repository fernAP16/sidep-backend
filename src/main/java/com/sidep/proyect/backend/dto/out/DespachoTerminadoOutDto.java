package com.sidep.proyect.backend.dto.out;

import java.util.Date;

public class DespachoTerminadoOutDto {
    private Date fechaRecojo; 
    private Date horaLlegada;
    private Date horaSalida;

    public DespachoTerminadoOutDto() {
    }
    
    public Date getFechaRecojo() {
        return fechaRecojo;
    }
    public void setFechaRecojo(Date fechaRecojo) {
        this.fechaRecojo = fechaRecojo;
    }
    public Date getHoraLlegada() {
        return horaLlegada;
    }
    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    public Date getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    
}
