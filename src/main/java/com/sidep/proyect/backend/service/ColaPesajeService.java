package com.sidep.proyect.backend.service;

import com.sidep.proyect.backend.dto.in.ColaPesajeDatosInDto;
import com.sidep.proyect.backend.dto.in.ColaPesajeRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeRegistrarOutDto;

public interface ColaPesajeService {
    
    public ColaPesajeRegistrarOutDto registrarTurnoBalanza(ColaPesajeRegistrarInDto inDto); 

    public ColaPesajeDatosOutDto obtenerDatosColaPesaje(ColaPesajeDatosInDto inDto);

}
