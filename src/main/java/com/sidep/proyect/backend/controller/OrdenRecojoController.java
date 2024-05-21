package com.sidep.proyect.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.OrdenActualizarPorDespachoInDto;
import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.service.OrdenRecojoService;
import com.sidep.proyect.backend.service.OrdenRecojoServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/ordenRecojo")
public class OrdenRecojoController {

    @Autowired
    private OrdenRecojoService ordenRecojoService;

    @Autowired
    private OrdenRecojoServiceInterface ordenRecojoServiceInterface;


    @GetMapping("/listByConductor/{idConductor}")
    public ResponseEntity<List<OrdenRecojo>> getOrdenesRecojoPorConductor(@PathVariable Integer idConductor) {
        List<OrdenRecojo> ordenes = ordenRecojoService.getOrdenesRecojoPorConductor(idConductor);
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping("/nuevoEstado")
    public Integer actualizarOrdenPorDespacho(@RequestBody OrdenActualizarPorDespachoInDto inDto) {
        return ordenRecojoServiceInterface.actualizarOrdenPorDespacho(inDto);
    }
    
    
}
