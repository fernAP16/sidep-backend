package com.sidep.proyect.backend.service;

import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaRegistrarOutDto;

public interface ColaCanalService {
    
    public ColaCargaRegistrarOutDto registrarTurnoCarga(ColaCargaRegistrarInDto inDto);  

}
