package com.sidep.proyect.backend.service.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.OrdenActualizarPorDespachoInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.OrdenRecojoServiceInterface;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class OrdenRecojoServiceInterfaceImpl implements OrdenRecojoServiceInterface{

    @Autowired
    private CrudService crudService ;

    @Override
    public Integer actualizarOrdenPorDespacho(OrdenActualizarPorDespachoInDto inDto){
        Query queryOrden = actualizarOrden(inDto);
        int filasActualizadas = queryOrden.executeUpdate();
        if(filasActualizadas > 0){
            return 1;
        }
        return 0;
    }

    private Query actualizarOrden(OrdenActualizarPorDespachoInDto inDto){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_orden_recojo ");
        sql.append("SET id_estado_orden = :idNuevoEstado ");
        sql.append("WHERE id_orden_recojo IN ( ");
        sql.append("SELECT id_orden_recojo ");
        sql.append("FROM sd_despacho ");
        sql.append("WHERE id_despacho = :idDespacho ");
        sql.append(") ");
        parameters.put("idDespacho", inDto.getIdDespacho());
        parameters.put("idNuevoEstado", inDto.getIdNuevoEstado());

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }
}
