package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.model.Incidencia;
import com.sidep.proyect.backend.repository.IncidenciaRepository;

@RestController
@RequestMapping(value = "/api/incidencia")
public class IncidenciaController {
    
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @GetMapping("/listAll")
    public List<Incidencia> obtenerDetalleDespachoPorId() {
        return incidenciaRepository.findAll();
    }
}
