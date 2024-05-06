package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.service.TurnoRevisionService;

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
    
}
