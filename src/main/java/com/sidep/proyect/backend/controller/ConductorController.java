package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.LoginInDto;
import com.sidep.proyect.backend.dto.out.LoginOutDto;
import com.sidep.proyect.backend.model.Conductor;
import com.sidep.proyect.backend.repository.ConductorRepository;
import com.sidep.proyect.backend.service.LoginService;

@RestController
@RequestMapping("/api/conductor")
public class ConductorController {
    
    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private LoginService loginService;

    @GetMapping("/listAll")
    public List<Conductor> getAllConductor() {
        return conductorRepository.findAll();
    }

    @PostMapping("/auth")
    public ResponseEntity<?> loginConductor(@RequestBody LoginInDto loginDTO)
    {
        LoginOutDto loginResponse = loginService.loginConductor(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
