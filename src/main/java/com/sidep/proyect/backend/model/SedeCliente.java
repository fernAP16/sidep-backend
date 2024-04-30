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
@Table(name = "sd_sede_cliente")
public class SedeCliente {
    @Id
    @Column(name = "id_sede_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSedeCliente;

    @JoinColumn(name = "id_cliente")
    @ManyToOne
    @NotNull
    private Cliente cliente;

    @Column(name = "direccion")
    @Size(max = 100, message = "direccion: maximo 100 caracteres")
    @NotBlank
    private String direccion;

    @Column(name = "distrito")
    @Size(max = 100, message = "distrito: maximo 100 caracteres")
    @NotBlank
    private String distrito;

    @Embedded
    @Valid
    private Auditoria auditoria;
    
    public SedeCliente() {
    }

    public Integer getIdSedeCliente() {
        return idSedeCliente;
    }

    public void setIdSedeCliente(Integer idSedeCliente) {
        this.idSedeCliente = idSedeCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "SedeCliente [idSedeCliente=" + idSedeCliente + ", cliente=" + cliente + ", direccion=" + direccion
                + ", distrito=" + distrito + ", auditoria=" + auditoria + "]";
    }

    

    
}
