package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_zona_balanza")
public class ZonaBalanza {
    @Id
    @Column(name = "id_zona_balanza")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idZonaBalanza;

    @JoinColumn(name = "id_planta")
    @ManyToOne
    @NotNull
    private Planta planta;

    @Column(name = "codigo")
    @Size(max = 10, message = "codigo: maximo 10 caracteres")
    @NotBlank
    private String codigo;

    @Column(name = "qr_fisico")
    @Size(max = 50, message = "qr_fisico: maximo 50 caracteres")
    @NotBlank
    private String qrFisico;

    @Column(name = "contrasena")
    @Size(max = 50, message = "contrasena: maximo 50 caracteres")
    @NotBlank
    private String contrasena;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public ZonaBalanza() {
        
    }

    public Integer getIdZonaBalanza() {
        return idZonaBalanza;
    }

    public void setIdZonaBalanza(Integer idZonaBalanza) {
        this.idZonaBalanza = idZonaBalanza;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getQrFisico() {
        return qrFisico;
    }

    public void setQrFisico(String qrFisico) {
        this.qrFisico = qrFisico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        return "ZonaBalanza [idZonaBalanza=" + idZonaBalanza + ", planta=" + planta + ", codigo=" + codigo
                + ", qrFisico=" + qrFisico + ", contrasena=" + contrasena + ", auditoria=" + auditoria + "]";
    }

    

    

}
