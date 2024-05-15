package com.sidep.proyect.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.dto.out.PuntosControlPlantaOutDto;

@Service
public interface PuntosControlService {
    
    public List<PuntosControlPlantaOutDto> listarPuntosControlPorPlanta(Integer idPlanta);
    
}
