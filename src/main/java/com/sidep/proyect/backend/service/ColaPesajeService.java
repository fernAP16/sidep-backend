package com.sidep.proyect.backend.service;

import com.sidep.proyect.backend.dto.in.ColaPesajeVacioInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeVacioOutDto;

public interface ColaPesajeService {
    
    public ColaPesajeVacioOutDto registrarTurnoBalanza(ColaPesajeVacioInDto inDto); 
}
