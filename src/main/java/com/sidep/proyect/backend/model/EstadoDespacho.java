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
@Table(name = "sd_estado_despacho")
public class EstadoDespacho {

    @Id
    @Column(name = "id_estado_despacho")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoDespacho;
    
    @Column(name = "nombre")
    @Size(max = 100, message = "nombre: maximo 100 caracteres")
    @NotBlank
    private String nombre;

    public EstadoDespacho() {
    }

    public Integer getIdEstadoDespacho() {
        return idEstadoDespacho;
    }

    public void setIdEstadoDespacho(Integer idEstadoDespacho) {
        this.idEstadoDespacho = idEstadoDespacho;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EstadoDespacho [idEstadoDespacho=" + idEstadoDespacho + ", nombre=" + nombre + "]";
    }

    
}
