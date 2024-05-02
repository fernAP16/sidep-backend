package com.sidep.proyect.backend.service;

import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;

public interface DespachoService {
    
    DespachoRegisterOutDto registrarDespacho(DespachoRegisterInDto inDto);
        
}
