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
@Table(name = "sd_estado_orden")
public class EstadoOrden {

    @Id
    @Column(name = "id_estado_orden")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoOrden;

    @Column(name = "nombre")
    @Size(max = 100, message = "nombre: maximo 100 caracteres")
    @NotBlank
    private String nombre;

    public EstadoOrden() {
    }

    public Integer getIdEstadoOrden() {
        return idEstadoOrden;
    }

    public void setIdEstadoOrden(Integer idEstadoOrden) {
        this.idEstadoOrden = idEstadoOrden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EstadoOrden [idEstadoOrden=" + idEstadoOrden + ", nombre=" + nombre + "]";
    }

    
}
