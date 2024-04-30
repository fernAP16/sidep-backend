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
@Table(name = "sd_tipo_vehiculo")
public class TipoVehiculo {
    @Id
    @Column(name = "id_tipo_vehiculo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoVehiculo;

    @Column(name = "nombre")
    @Size(max = 20, message = "nombre: maximo 20 caracteres")
    @NotBlank
    private String nombre;

    public TipoVehiculo() {
    }

    public Integer getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(Integer idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoVehiculo [idTipoVehiculo=" + idTipoVehiculo + ", nombre=" + nombre + "]";
    }

    
}
