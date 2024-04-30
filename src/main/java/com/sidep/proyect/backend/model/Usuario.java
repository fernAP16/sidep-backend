package com.sidep.proyect.backend.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sd_usuario")
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(name = "nombres")
    @Size(max = 100, message = "nombres: maximo 100 caracteres")
    @NotBlank
    private String nombres;

    @Column(name = "primer_apellido")
    @Size(max = 100, message = "primer_apellido: maximo 100 caracteres")
    @NotBlank
    private String primerApellido;

    @Column(name = "segundo_apellido")
    @Size(max = 100, message = "segundo_apellido: maximo 100 caracteres")
    @NotBlank
    private String segundoApelido;

    @Column(name = "dni")
    @Size(max = 8, message = "dni: maximo 8 caracteres")
    @NotBlank
    private String dni;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_nacimiento")
    @NotNull
    private Date fechaNacimiento;

    @Column(name = "peso")
    @NotNull
    private Double peso;

    @Embedded
    @Valid
    private Auditoria auditoria;    

    public Usuario() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getPrimerApellido() {
        return primerApellido;
    }
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }
    public String getSegundoApelido() {
        return segundoApelido;
    }
    public void setSegundoApelido(String segundoApelido) {
        this.segundoApelido = segundoApelido;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Auditoria getAuditoria() {
        return auditoria;
    }
    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombres=" + nombres + ", primerApellido=" + primerApellido
                + ", segundoApelido=" + segundoApelido + ", dni=" + dni + ", fechaNacimiento=" + fechaNacimiento
                + ", auditoria=" + auditoria + "]";
    }

    
}
