package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaRegistrarOutDto;
import com.sidep.proyect.backend.service.ColaCanalService;

@RestController
@RequestMapping("/api/colacarga")
public class ColaCanalController {
    
    @Autowired
    private ColaCanalService colaCanalService;

    @PostMapping("/registrar")
    public ColaCargaRegistrarOutDto registrarTurnoCarga(@RequestBody ColaCargaRegistrarInDto inDto) {
        return colaCanalService.registrarTurnoCarga(inDto);
    }

    
}
