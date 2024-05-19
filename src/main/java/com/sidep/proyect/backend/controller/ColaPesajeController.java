package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.ColaPesajeVacioInDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeVacioOutDto;
import com.sidep.proyect.backend.service.ColaPesajeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/colapesaje")
public class ColaPesajeController {
    
    @Autowired
    private ColaPesajeService colaPesajeService;

    @PostMapping("/registrarvacio")
    public ColaPesajeVacioOutDto registrarTurnoBalanza(ColaPesajeVacioInDto inDto) {
        return colaPesajeService.registrarTurnoBalanza(inDto);
    }

}
