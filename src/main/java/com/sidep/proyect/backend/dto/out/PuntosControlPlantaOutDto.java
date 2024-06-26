package com.sidep.proyect.backend.dto.out;

public class PuntosControlPlantaOutDto {
    
    private Integer idPuntoControl;
    private String codigoPuntoControl;
    private Integer idRevisorAsignado;
    private Integer idTurnoRevision;
    
    public PuntosControlPlantaOutDto() {
    }

    public Integer getIdPuntoControl() {
        return idPuntoControl;
    }

    public void setIdPuntoControl(Integer idPuntoControl) {
        this.idPuntoControl = idPuntoControl;
    }

    public String getCodigoPuntoControl() {
        return codigoPuntoControl;
    }

    public void setCodigoPuntoControl(String codigoPuntoControl) {
        this.codigoPuntoControl = codigoPuntoControl;
    }

    public Integer getIdRevisorAsignado() {
        return idRevisorAsignado;
    }

    public void setIdRevisorAsignado(Integer idRevisorAsignado) {
        this.idRevisorAsignado = idRevisorAsignado;
    }

    public Integer getIdTurnoRevision() {
        return idTurnoRevision;
    }

    public void setIdTurnoRevision(Integer idTurnoRevision) {
        this.idTurnoRevision = idTurnoRevision;
    }

    

}
