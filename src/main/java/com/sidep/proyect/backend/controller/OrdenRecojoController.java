package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.service.OrdenRecojoService;


@RestController
@RequestMapping("/api/ordenRecojo")
public class OrdenRecojoController {
    private final OrdenRecojoService ordenRecojoService;

    public OrdenRecojoController(OrdenRecojoService ordenRecojoService) {
        this.ordenRecojoService = ordenRecojoService;
    }

    @GetMapping("listByConductor/{idConductor}")
    public ResponseEntity<List<OrdenRecojo>> getOrdenesRecojoPorConductor(@PathVariable Integer idConductor) {
        System.out.println(idConductor);
        List<OrdenRecojo> ordenes = ordenRecojoService.getOrdenesRecojoPorConductor(idConductor);
        return ResponseEntity.ok(ordenes);
    }
    
}
