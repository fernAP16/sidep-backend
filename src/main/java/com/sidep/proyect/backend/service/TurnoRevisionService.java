package com.sidep.proyect.backend.service;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionAsignarOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionDespachoOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;

@Service
public interface TurnoRevisionService {

    TurnoRevisionOutDto registrarTurnoRevision(TurnoRevisionInDto inDto);

    TurnoRevisionOutDto obtenerTurnoRevision(TurnoRevisionInDto inDto);

    TurnoRevisionDespachoOutDto obtenerDatosDespacho(Integer idTurnoRevision);

    TurnoRevisionAsignarOutDto asignarRevisorYPuntoControlATurnoRevision(TurnoRevisionAsignarInDto inDto);
}
