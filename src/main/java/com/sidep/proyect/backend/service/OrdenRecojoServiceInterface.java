package com.sidep.proyect.backend.service;

import com.sidep.proyect.backend.dto.in.OrdenActualizarPorDespachoInDto;

public interface OrdenRecojoServiceInterface {
    
    public Integer actualizarOrdenPorDespacho(OrdenActualizarPorDespachoInDto inDto);
    
}
