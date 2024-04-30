package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.model.Conductor;
import com.sidep.proyect.backend.repository.ConductorRepository;

@RestController
@RequestMapping("/api/conductor")
public class ConductorController {
    
    @Autowired
    private ConductorRepository conductorRepository;

    @GetMapping("/listAll")
    public List<Conductor> getAllConductor() {
        return conductorRepository.findAll();
    }

    // @GetMapping("/auth")
    // public Conductor authentication(String dni, String contrasena) {
    //     return conductorRepository.authenticateConductor(dni, contrasena);
    // }
    
}
