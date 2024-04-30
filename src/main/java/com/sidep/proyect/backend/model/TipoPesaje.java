package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_tipo_pesaje")
public class TipoPesaje {
    @Id
    @Column(name = "id_tipo_pesaje")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPesaje;

    @Column(name = "nombre")
    @Size(max = 100, message = "nombre: maximo 100 caracteres")
    @NotBlank
    private String nombre;

    public TipoPesaje() {
    }

    public Integer getIdTipoPesaje() {
        return idTipoPesaje;
    }

    public void setIdTipoPesaje(Integer idTipoPesaje) {
        this.idTipoPesaje = idTipoPesaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoPesaje [idTipoPesaje=" + idTipoPesaje + ", nombre=" + nombre + "]";
    }

    
}
