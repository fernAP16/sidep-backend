package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.service.DespachoService;

@RestController
@RequestMapping(value = "/api/despacho")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @PostMapping("/registrar")
    public DespachoRegisterOutDto registrarDespacho(@RequestBody DespachoRegisterInDto inDto) {
        return despachoService.registrarDespacho(inDto);
    }
}
