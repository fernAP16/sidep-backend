package com.sidep.proyect.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Embeddable
public class Auditoria {
    
    @Column(name = "activo")
    @NotNull
    @Min(0)
    @Max(1)
    private Integer activo;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro")
    @NotNull
    private Date fechaRegistro;

    @Column(name = "usuario_registro")
    @NotBlank
    @Size(max = 100, message = "qr_fisico: maximo 100 caracteres")
    private String usuarioRegistro;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    @NotNull
    private Date fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    @NotNull
    @Size(max = 100, message = "usuario_actualizacion: maximo 100 caracteres")
    private String usuarioActualizacion;

    public Auditoria() {
    }
    
    public Integer getActivo() {
        return activo;
    }
    public void setActivo(Integer activo) {
        this.activo = activo;
    }
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }
    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }
    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
    
    @Override
    public String toString() {
        return "Auditoria [activo=" + activo + ", fechaRegistro=" + fechaRegistro + ", usuarioRegistro="
                + usuarioRegistro + ", fechaActualizacion=" + fechaActualizacion + ", usuarioActualizacion="
                + usuarioActualizacion + "]";
    }
}
