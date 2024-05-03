package com.sidep.proyect.backend.dto.in;

public class LoginInDto {
    
    private String username;
    private String password;

    public LoginInDto() {

    }

    public LoginInDto(String username, String password, Integer type) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

}
