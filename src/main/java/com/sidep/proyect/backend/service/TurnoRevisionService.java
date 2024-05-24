package com.sidep.proyect.backend.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionIncidenciaInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionAsignarOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionConductorOutDto;
import com.sidep.proyect.backend.dto.out.DespachoDatosOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;

@Service
public interface TurnoRevisionService {

    public TurnoRevisionOutDto registrarTurnoRevision(TurnoRevisionInDto inDto);

    public TurnoRevisionOutDto obtenerTurnoRevision(TurnoRevisionInDto inDto);

    public DespachoDatosOutDto obtenerDatosDespacho(Integer idTurnoRevision);

    public TurnoRevisionAsignarOutDto asignarRevisorYPuntoControlATurnoRevision(TurnoRevisionAsignarInDto inDto);

    public Integer aprobarRevision(Integer idTurnoRevision);

    public Integer registrarIncidencias(TurnoRevisionIncidenciaInDto inDto);

    public TurnoRevisionConductorOutDto obtenerDatosRevisionDelConductor(Integer idDespacho)  throws ParseException;

    public Integer salirPuntoControl(Integer idRevisor);

    public List<String> obtenerIncidenciasPorRevision(Integer idTurnoRevision);
}
