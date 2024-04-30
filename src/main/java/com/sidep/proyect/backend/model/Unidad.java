package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_unidad")
public class Unidad {
    @Id
    @Column(name = "id_unidad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnidad;

    @Column(name = "nombre")
    @Size(max = 50, message = "nombre: maximo 50 caracteres")
    @NotBlank
    private String nombre;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public Unidad() {
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Unidad [idUnidad=" + idUnidad + ", nombre=" + nombre + ", auditoria=" + auditoria + "]";
    }

    
}
