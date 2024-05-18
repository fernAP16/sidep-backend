package com.sidep.proyect.backend.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionIncidenciaInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionAsignarOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionConductorOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionDespachoOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;

@Service
public interface TurnoRevisionService {

    TurnoRevisionOutDto registrarTurnoRevision(TurnoRevisionInDto inDto);

    TurnoRevisionOutDto obtenerTurnoRevision(TurnoRevisionInDto inDto);

    TurnoRevisionDespachoOutDto obtenerDatosDespacho(Integer idTurnoRevision);

    TurnoRevisionAsignarOutDto asignarRevisorYPuntoControlATurnoRevision(TurnoRevisionAsignarInDto inDto);

    Integer aprobarRevision(Integer idTurnoRevision);

    Integer registrarIncidencias(TurnoRevisionIncidenciaInDto inDto);

    TurnoRevisionConductorOutDto obtenerDatosRevisionDelConductor(Integer idDespacho)  throws ParseException;
}
