package com.sidep.proyect.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sidep.proyect.backend.dto.in.ColaCanalFinalizarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaVerificarInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDespachoOutDto;
import com.sidep.proyect.backend.dto.out.ColaCargaRegistrarOutDto;
import com.sidep.proyect.backend.service.ColaCanalService;

@RestController
@RequestMapping("/api/colacarga")
public class ColaCanalController {
    
    @Autowired
    private ColaCanalService colaCanalService;

    @PostMapping("/registrar")
    public ColaCargaRegistrarOutDto registrarTurnoCarga(@RequestBody ColaCargaRegistrarInDto inDto) {
        return colaCanalService.registrarTurnoCarga(inDto);
    }

    @PostMapping("/obtenerdatos/{idDespacho}")
    public ColaCargaDatosOutDto obtenerDatos(@PathVariable Integer idDespacho) {
        return colaCanalService.obtenerDatosColaCarga(idDespacho);
    }

    @PostMapping("/verificar")
    public Integer verificarCanalCarga(@RequestBody ColaCargaVerificarInDto inDto) {
        return colaCanalService.verificarCanalCarga(inDto);
    }
    
    
    @PostMapping("/obtenerdatos/completos/{idDespacho}")
    public ColaCargaDespachoOutDto obtenerDatosCompletosCarga(@PathVariable Integer idDespacho){
        return colaCanalService.obtenerDatosCompletosCarga(idDespacho);
    }

    @PostMapping("/finalizarcarga")
    public Integer finalizarCarga(@RequestBody ColaCanalFinalizarInDto inDto){
        return colaCanalService.finalizarCarga(inDto);
    }
}
