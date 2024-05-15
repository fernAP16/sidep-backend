package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.out.PuntosControlPlantaOutDto;
import com.sidep.proyect.backend.service.PuntosControlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/puntocontrol")
public class PuntoControlController {

    @Autowired
    private PuntosControlService puntoControlService;

    @GetMapping("/listByPlanta/{idPlanta}")
    public List<PuntosControlPlantaOutDto> listarPuntosControlPorPlanta(@PathVariable Integer idPlanta) {
        return puntoControlService.listarPuntosControlPorPlanta(idPlanta);
    }
    

}
