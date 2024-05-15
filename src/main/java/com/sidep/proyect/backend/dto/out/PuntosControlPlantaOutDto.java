package com.sidep.proyect.backend.dto.out;

public class PuntosControlPlantaOutDto {
    
    private Integer idPuntoControl;
    private String codigoPuntoControl;
    private Integer disponible;
    
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

    public Integer getDisponible() {
        return disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    

}
