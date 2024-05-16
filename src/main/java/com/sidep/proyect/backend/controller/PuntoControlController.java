package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.CoordenadasPlantaInDto;
import com.sidep.proyect.backend.dto.out.PuntosControlPlantaOutDto;
import com.sidep.proyect.backend.service.PuntosControlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/puntocontrol")
public class PuntoControlController {

    @Autowired
    private PuntosControlService puntoControlService;

    @PostMapping("/listByPlanta")
    public List<PuntosControlPlantaOutDto> listarPuntosControlPorPlanta(@RequestBody CoordenadasPlantaInDto inDto) {
        return puntoControlService.listarPuntosControlPorPlanta(inDto);
    }
    

}
