package com.sidep.proyect.backend.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionIncidenciaInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionAsignarOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionConductorOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionDespachoOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.service.TurnoRevisionService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/turnorevision")
public class TurnoRevisionController {
    
    @Autowired
    private TurnoRevisionService turnoRevisionService;

    @PostMapping("/registrar")
    public TurnoRevisionOutDto registrarTurnoRevision(@RequestBody TurnoRevisionInDto inDto) {
        return turnoRevisionService.registrarTurnoRevision(inDto);
    }

    @PostMapping("/obtener")
    public TurnoRevisionOutDto obtenerTurnoRevision(@RequestBody TurnoRevisionInDto inDto){
        return turnoRevisionService.obtenerTurnoRevision(inDto);
    }

    @PostMapping("/obtenerDespachoById/{idTurnoRevision}")
    public TurnoRevisionDespachoOutDto obtenerDatosDespacho(@PathVariable Integer idTurnoRevision){
        return turnoRevisionService.obtenerDatosDespacho(idTurnoRevision);
    }

    @PostMapping("/asignarTurnoRevision")
    public TurnoRevisionAsignarOutDto asignarRevisorYPuntoControlATurnoRevision(@RequestBody TurnoRevisionAsignarInDto inDto) {
        return turnoRevisionService.asignarRevisorYPuntoControlATurnoRevision(inDto);
    }

    @PostMapping("/aprobar/{idTurnoRevision}")
    public Integer aprobarRevision(@PathVariable Integer idTurnoRevision) {
        return turnoRevisionService.aprobarRevision(idTurnoRevision);
    }
    
    @PostMapping("/registrarIncidencia")
    public Integer registrarIncidencias(@RequestBody TurnoRevisionIncidenciaInDto inDto) {
        return turnoRevisionService.registrarIncidencias(inDto);
    }

    @PostMapping("/obtenerDatos/conductor/{idDespacho}")
    public TurnoRevisionConductorOutDto obtenerDatosRevisionDelConductor(@PathVariable Integer idDespacho)  throws ParseException{
        return turnoRevisionService.obtenerDatosRevisionDelConductor(idDespacho);
    }
    
    
    
}
