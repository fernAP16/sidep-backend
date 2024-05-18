package com.sidep.proyect.backend.controller;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.DespachoActualizarEstadoInDto;
import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoObtenerVigenteOutDto;
import com.sidep.proyect.backend.dto.out.DespachoPorOrdenOutDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.repository.DespachoRepository;
import com.sidep.proyect.backend.service.DespachoService;



@RestController
@RequestMapping(value = "/api/despacho")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @Autowired
    private DespachoRepository despachoRepository;

    @PostMapping("/registrar")
    public DespachoRegisterOutDto registrarDespacho(@RequestBody DespachoRegisterInDto inDto) throws ParseException {
        return despachoService.registrarDespacho(inDto);
    }

    @GetMapping("/detalle/{idDespacho}")
    public Optional<Despacho> obtenerDetalleDespachoPorId(@PathVariable Integer idDespacho) {
        return despachoRepository.findByIdDespacho(idDespacho);
    }

    @GetMapping("/estadoDespacho/{idConductor}")
    public DespachoObtenerVigenteOutDto obtenerDespachoVigentePorConductor(@PathVariable Integer idConductor) {
        return despachoService.obtenerDespachoVigentePorConductor(idConductor);
    }
    
    @PostMapping("/orden/{idOrden}")
    public DespachoPorOrdenOutDto obtenerUltimoDespachoPorOden(@PathVariable Integer idOrden) {
        return despachoService.obtenerUltimoDespachoPorOrden(idOrden);
    }

    @PostMapping("/nuevoEstado")
    public Integer actualizarEstadoDespacho(@RequestBody DespachoActualizarEstadoInDto inDto) {        
        return despachoService.actualizarEstadoDespacho(inDto);
    }
    
    
}
