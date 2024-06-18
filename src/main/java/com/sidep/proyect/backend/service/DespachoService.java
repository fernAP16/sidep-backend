package com.sidep.proyect.backend.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sidep.proyect.backend.dto.in.DespachoActualizarEstadoInDto;
import com.sidep.proyect.backend.dto.in.DespachoNuevoPesajeInDto;
import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.in.SalidaDespachoInDto;
import com.sidep.proyect.backend.dto.out.DespachoObtenerVigenteOutDto;
import com.sidep.proyect.backend.dto.out.DespachoPorOrdenOutDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.dto.out.DespachoTerminadoOutDto;

@Service
public interface DespachoService {
    
    public DespachoRegisterOutDto registrarDespacho(DespachoRegisterInDto inDto) throws ParseException;

    public DespachoObtenerVigenteOutDto obtenerDespachoVigentePorConductor(Integer idConductor);

    public DespachoPorOrdenOutDto obtenerUltimoDespachoPorOrden(Integer idOrden);

    public DespachoTerminadoOutDto obtenerDatosDespachoTerminado(Integer idOrden)  throws ParseException ;

    public Integer actualizarEstadoDespacho(DespachoActualizarEstadoInDto inDto);

    public Integer actualizarValorPesaje(DespachoNuevoPesajeInDto inDto);

    public Date registrarSalidaDespacho(SalidaDespachoInDto inDto)  throws ParseException;
        
}
