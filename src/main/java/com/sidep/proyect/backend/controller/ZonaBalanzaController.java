package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.LoginInDto;
import com.sidep.proyect.backend.dto.out.LoginOutDto;
import com.sidep.proyect.backend.service.LoginService;

@RestController
@RequestMapping("/api/zonabalanza")
public class ZonaBalanzaController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/auth")
    public ResponseEntity<?> loginBalanza(@RequestBody LoginInDto loginDTO)
    {
        LoginOutDto loginResponse = loginService.loginBalanza(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
