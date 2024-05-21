package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.ColaPesajeDatosInDto;
import com.sidep.proyect.backend.dto.in.ColaPesajeRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeRegistrarOutDto;
import com.sidep.proyect.backend.service.ColaPesajeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/colapesaje")
public class ColaPesajeController {
    
    @Autowired
    private ColaPesajeService colaPesajeService;

    @PostMapping("/registrar")
    public ColaPesajeRegistrarOutDto registrarTurnoBalanza(@RequestBody ColaPesajeRegistrarInDto inDto) {
        return colaPesajeService.registrarTurnoBalanza(inDto);
    }

    @PostMapping("/obtenerdatos")
    public ColaPesajeDatosOutDto obtenerDatosColaPesaje(@RequestBody ColaPesajeDatosInDto inDto) {
        return colaPesajeService.obtenerDatosColaPesaje(inDto);
    }

}
