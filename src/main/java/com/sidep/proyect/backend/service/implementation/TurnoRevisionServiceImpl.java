package com.sidep.proyect.backend.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.TurnoRevision;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.TurnoRevisionService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class TurnoRevisionServiceImpl implements TurnoRevisionService{

    @Autowired
    private CrudService crudService ;

    @Override
    public TurnoRevisionOutDto registrarTurnoRevision(TurnoRevisionInDto inDto) {

        TurnoRevisionOutDto outDto = new TurnoRevisionOutDto();
        TurnoRevision turnoRevision = new TurnoRevision();
        Integer turnoAsignado = obtenerTurnoAsignado(inDto.getIdPlanta());
        Integer turnoActual = obtenerTurnoActual(inDto.getIdPlanta());
        if(turnoAsignado == null) turnoAsignado = 0;
        turnoAsignado += 1;
        if(turnoActual == null) turnoActual = 0;
        turnoActual += 1;
        turnoRevision.setDespacho(new Despacho());
        turnoRevision.getDespacho().setIdDespacho(inDto.getIdDespacho());
        turnoRevision.setTurnoDia(turnoAsignado);
        turnoRevision.setAuditoria(new Auditoria());
        turnoRevision.getAuditoria().setActivo(1);
        turnoRevision.getAuditoria().setFechaRegistro(new Date());
        turnoRevision.getAuditoria().setUsuarioRegistro("usuario");

        crudService.create(turnoRevision);

        outDto.setIdTurnoRevision(turnoRevision.getIdTurnoRevision());
        outDto.setTurnoActual(turnoActual);
        outDto.setTurnoAsignado(turnoAsignado);

        return outDto;
    }

    private Integer obtenerTurnoAsignado(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(tr.turno_dia) as ultimo_turno FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho dp ON tr.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON dp.id_planta = pl.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta AND DATE(tr.fecha_registro) = CURDATE() ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    private Integer obtenerTurnoActual(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(tr.turno_dia) as turno_actual ");
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho dp ON tr.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = dp.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta ");
        sql.append("AND DATE(tr.fecha_registro) = CURDATE() ");
        sql.append("AND tr.hora_inicio IS NOT NULL ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    @Override
    public TurnoRevisionOutDto obtenerTurnoRevision(TurnoRevisionInDto inDto){
        TurnoRevisionOutDto outDto = new TurnoRevisionOutDto();
        Integer turnoActual = obtenerTurnoActual(inDto.getIdPlanta());
        Query query = consultarTurnoAsignado(inDto.getIdDespacho());
        List<Object[]> resultList = query.getResultList();
        if(turnoActual == null) turnoActual = 0;
        turnoActual += 1;
        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdTurnoRevision(QueryUtils.getAsInteger(item[0]));
            outDto.setTurnoAsignado(QueryUtils.getAsInteger(item[1]));
            outDto.setTurnoActual(turnoActual);
        } else {
            outDto.setIdTurnoRevision(0);
            outDto.setTurnoAsignado(0);
            outDto.setTurnoActual(0);
        }

        return outDto;
    }

    private Query consultarTurnoAsignado(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT tr.id_turno_revision, tr.turno_dia ");
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho ds ON tr.id_despacho = ds.id_despacho ");
        sql.append("WHERE ds.id_despacho = :idDespacho ");

        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }
}
