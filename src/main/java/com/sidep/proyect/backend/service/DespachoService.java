package com.sidep.proyect.backend.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoObtenerVigenteOutDto;
import com.sidep.proyect.backend.dto.out.DespachoPorOrdenOutDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;

@Service
public interface DespachoService {
    
    DespachoRegisterOutDto registrarDespacho(DespachoRegisterInDto inDto) throws ParseException;

    DespachoObtenerVigenteOutDto obtenerDespachoVigentePorConductor(Integer idConductor);

    DespachoPorOrdenOutDto obtenerUltimoDespachoPorOrden(Integer idOrden);
        
}
