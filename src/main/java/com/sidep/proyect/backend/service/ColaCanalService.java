package com.sidep.proyect.backend.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.sidep.proyect.backend.dto.in.ColaCanalFinalizarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaVerificarInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDespachoOutDto;
import com.sidep.proyect.backend.dto.out.ColaCargaRegistrarOutDto;

public interface ColaCanalService {
    
    public ColaCargaRegistrarOutDto registrarTurnoCarga(ColaCargaRegistrarInDto inDto);  

    public ColaCargaDatosOutDto obtenerDatosColaCarga(Integer idDespacho);

    public Integer verificarCanalCarga(ColaCargaVerificarInDto inDto);

    public ColaCargaDespachoOutDto obtenerDatosCompletosCarga(Integer idDespacho);

    public Integer finalizarCarga(ColaCanalFinalizarInDto inDto);

}
