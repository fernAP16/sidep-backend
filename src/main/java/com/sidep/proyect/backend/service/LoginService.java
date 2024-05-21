package com.sidep.proyect.backend.service;


import com.sidep.proyect.backend.dto.in.LoginInDto;
import com.sidep.proyect.backend.dto.out.LoginOutDto;

public interface LoginService {
    
    LoginOutDto loginConductor(LoginInDto inDto);

    LoginOutDto loginRevisor(LoginInDto inDto);

    LoginOutDto loginBalanza(LoginInDto inDto);
}