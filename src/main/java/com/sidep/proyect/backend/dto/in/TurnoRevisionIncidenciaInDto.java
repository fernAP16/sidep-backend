package com.sidep.proyect.backend.dto.in;

import java.util.List;

public class TurnoRevisionIncidenciaInDto {
    private List<Integer> idIncidencias;
    private Integer idTurnoRevision;

    public TurnoRevisionIncidenciaInDto() {
    }
    
    public List<Integer> getIdIncidencias() {
        return idIncidencias;
    }
    public void setIdIncidencias(List<Integer> idIncidencias) {
        this.idIncidencias = idIncidencias;
    }
    public Integer getIdTurnoRevision() {
        return idTurnoRevision;
    }
    public void setIdTurnoRevision(Integer idTurnoRevision) {
        this.idTurnoRevision = idTurnoRevision;
    }
    
}
