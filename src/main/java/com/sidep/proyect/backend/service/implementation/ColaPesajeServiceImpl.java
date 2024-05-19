package com.sidep.proyect.backend.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.in.ColaPesajeVacioInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeVacioOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.ColaPesaje;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.Planta;
import com.sidep.proyect.backend.model.TipoPesaje;
import com.sidep.proyect.backend.model.TurnoRevision;
import com.sidep.proyect.backend.model.ZonaBalanza;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.ColaPesajeService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class ColaPesajeServiceImpl implements ColaPesajeService{

    @Autowired
    private CrudService crudService ;
    
    @Override
    public ColaPesajeVacioOutDto registrarTurnoBalanza(ColaPesajeVacioInDto inDto) {
        ColaPesajeVacioOutDto outDto = new ColaPesajeVacioOutDto();

        Integer turnoAsignado = obtenerTurnoAsignado(inDto.getIdPlanta(), inDto.getTipoPesaje());
        Integer turnoActual = obtenerTurnoActual(inDto.getIdPlanta(), inDto.getTipoPesaje());
        if(turnoAsignado == null) turnoAsignado = 0;
        turnoAsignado += 1;
        if(turnoActual == null) turnoActual = 0;
        turnoActual += 1;
        ColaPesaje colaPesajeVacio = new ColaPesaje();
        colaPesajeVacio.setDespacho(new Despacho());
        colaPesajeVacio.getDespacho().setIdDespacho(inDto.getIdDespacho());
        colaPesajeVacio.setTipoPesaje(new TipoPesaje());
        colaPesajeVacio.getTipoPesaje().setIdTipoPesaje(inDto.getTipoPesaje());

        colaPesajeVacio.getPlanta().setIdPlanta(inDto.getIdPlanta());
        
        colaPesajeVacio.setAuditoria(new Auditoria());
        colaPesajeVacio.getAuditoria().setActivo(1);
        colaPesajeVacio.getAuditoria().setFechaRegistro(new Date());
        colaPesajeVacio.getAuditoria().setUsuarioRegistro("usuario");

        crudService.create(colaPesajeVacio);

        outDto.setIdColaBalanzaVacio(colaPesajeVacio.getIdColaPesaje());
        outDto.setTurnoActual(turnoActual);
        outDto.setTurnoAsignado(turnoAsignado);

        return outDto;
    }

    private Integer obtenerTurnoAsignado(Integer idPlanta, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(cp.posicion) as ultimo_turno ");
        sql.append("FROM sd_cola_pesaje cp ");
        sql.append("INNER JOIN sd_despacho dp ON cp.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON dp.id_planta = pl.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta ");
        sql.append("AND cp.id_tipo_pesaje = :idTipoPesaje ");
        sql.append("AND DATE(cp.fecha_registro) = CURDATE() ");
        parameters.put("idPlanta", idPlanta);
        parameters.put("idTipoPesaje", idTipoPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    private Integer obtenerTurnoActual(Integer idPlanta, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(cp.posicion) as turno_actual ");
        sql.append("FROM sd_cola_pesaje cp ");
        sql.append("INNER JOIN sd_despacho dp ON cp.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = dp.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta ");
        sql.append("AND cp.id_tipo_pesaje = :idTipoPesaje ");
        sql.append("AND DATE(tr.fecha_registro) = CURDATE() ");
        sql.append("AND tr.hora_inicio IS NOT NULL ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }
}
