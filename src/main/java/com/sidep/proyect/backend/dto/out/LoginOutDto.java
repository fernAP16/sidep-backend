package com.sidep.proyect.backend.dto.out;

public class LoginOutDto {
    
    String message;
    Boolean status;
    Integer id;
    String nombres;

    public LoginOutDto(String message, Boolean status, Integer id, String nombres) {
        this.message = message;
        this.status = status;
        this.id = id;
        this.nombres = nombres;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    @Override
    public String toString() {
        return "LoginOutDto [message=" + message + ", status=" + status + ", id=" + id + ", nombres=" + nombres + "]";
    }

    
    
}
