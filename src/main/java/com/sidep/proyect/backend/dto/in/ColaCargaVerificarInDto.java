package com.sidep.proyect.backend.dto.in;

public class ColaCargaVerificarInDto {
    private Integer idCanalCarga;
    private String qrLeido;
    
    public ColaCargaVerificarInDto() {
    }

    public Integer getIdCanalCarga() {
        return idCanalCarga;
    }

    public void setIdCanalCarga(Integer idCanalCarga) {
        this.idCanalCarga = idCanalCarga;
    }

    public String getQrLeido() {
        return qrLeido;
    }

    public void setQrLeido(String qrLeido) {
        this.qrLeido = qrLeido;
    }

    
}
