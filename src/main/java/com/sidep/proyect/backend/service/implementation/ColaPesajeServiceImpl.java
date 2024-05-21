package com.sidep.proyect.backend.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.ColaPesajeDatosInDto;
import com.sidep.proyect.backend.dto.in.ColaPesajeRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaPesajeRegistrarOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.ColaPesaje;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.TipoPesaje;
import com.sidep.proyect.backend.model.ZonaBalanza;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.ColaPesajeService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class ColaPesajeServiceImpl implements ColaPesajeService{

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ColaPesajeRegistrarOutDto registrarTurnoBalanza(ColaPesajeRegistrarInDto inDto) {
        ColaPesajeRegistrarOutDto outDto = new ColaPesajeRegistrarOutDto();
        Integer idZonaBalanzaSelected = 0, vehiculosEsperaSelected = 99999;
        Integer idZonaBalanza, vehiculosEspera, turnoAsignado;
        String balanzaAsignada = "", balanza;

        Query queryBalanzas = obtenerBalanzasPorPlanta(inDto.getIdPlanta());
        List<Object[]> resultListBanalzas = queryBalanzas.getResultList();
        for (Object[] item : resultListBanalzas) {
            idZonaBalanza = QueryUtils.getAsInteger(item[0]);
            balanza = QueryUtils.getAsString(item[1]);
            vehiculosEspera = obtenerVehiculosEspera(idZonaBalanza, inDto.getTipoPesaje());
            if(vehiculosEspera == null)
                vehiculosEspera = 0;
            if(vehiculosEsperaSelected > vehiculosEspera){
                idZonaBalanzaSelected = idZonaBalanza;
                vehiculosEsperaSelected = vehiculosEspera;
                balanzaAsignada = balanza;
            }
        }
        turnoAsignado = vehiculosEsperaSelected + 1;
        if(turnoAsignado == 6){
            outDto.setIdColaBalanzaNuevo(0);
            outDto.setTurnoAsignado(0);
            return outDto;
        }

        Despacho despacho = entityManager.find(Despacho.class, inDto.getIdDespacho());
        TipoPesaje tipoPesaje = entityManager.find(TipoPesaje.class, inDto.getTipoPesaje());
        ZonaBalanza zonaBalanza = entityManager.find(ZonaBalanza.class, idZonaBalanzaSelected);

        ColaPesaje colaPesajeVacio = new ColaPesaje();
        colaPesajeVacio.setDespacho(despacho);
        colaPesajeVacio.setTipoPesaje(tipoPesaje);
        colaPesajeVacio.setZonaBalanza(zonaBalanza);
        colaPesajeVacio.setPosicion(turnoAsignado);
        colaPesajeVacio.setAuditoria(new Auditoria());
        colaPesajeVacio.getAuditoria().setActivo(1);
        colaPesajeVacio.getAuditoria().setFechaRegistro(new Date());
        colaPesajeVacio.getAuditoria().setUsuarioRegistro("usuario");

        crudService.create(colaPesajeVacio);

        outDto.setIdColaBalanzaNuevo(colaPesajeVacio.getIdColaPesaje());
        outDto.setTurnoAsignado(turnoAsignado);
        outDto.setBalanzaAsignada(balanzaAsignada);

        return outDto;
    }

    private Query obtenerBalanzasPorPlanta(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT id_zona_balanza, codigo ");
        sql.append("FROM sd_zona_balanza ");
        sql.append("WHERE id_planta = :idPlanta ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Integer obtenerVehiculosEspera(Integer idZonaBalanza, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) as vehiculos_espera ");
        sql.append("FROM sd_cola_pesaje ");
        sql.append("WHERE id_zona_balanza = :idZonaBalanza ");
        sql.append("AND id_tipo_pesaje = :idTipoPesaje ");
        sql.append("AND posicion <> 0 ");
        parameters.put("idZonaBalanza", idZonaBalanza);
        parameters.put("idTipoPesaje", idTipoPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        Object result = query.getSingleResult();
        if (result != null) {
            return ((Number) result).intValue();
        } else {
            return 0;
        }
    }

    @Override
    public ColaPesajeDatosOutDto obtenerDatosColaPesaje(ColaPesajeDatosInDto inDto){
        ColaPesajeDatosOutDto outDto = new ColaPesajeDatosOutDto();
        Double valorPesaje;
        Query query = consultarDatosPesaje(inDto.getIdDespacho(), inDto.getTipoPesaje());

        List<Object[]> resultList = query.getResultList();
        
        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdColaPesaje(QueryUtils.getAsInteger(item[0]));
            outDto.setIdZonaBalanza(QueryUtils.getAsInteger(item[1]));
            outDto.setCodigoZonaBalanza(QueryUtils.getAsString(item[2]));
        } else {
            outDto.setIdColaPesaje(0);
            outDto.setIdZonaBalanza(0);
            outDto.setCodigoZonaBalanza("");
        }

        valorPesaje = obtenerValorPesaje(inDto.getIdDespacho(), inDto.getTipoPesaje());
        outDto.setValorPesaje(valorPesaje);

        return outDto;
    }

    private Query consultarDatosPesaje(Integer idDespacho, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        sql.append("SELECT cp.id_cola_pesaje, zb.id_zona_balanza, zb.codigo ");
        sql.append("FROM sd_zona_balanza zb ");
        sql.append("INNER JOIN sd_cola_pesaje cp ON cp.id_zona_balanza = zb.id_zona_balanza ");
        sql.append("WHERE cp.id_despacho = :idDespacho AND cp.id_tipo_pesaje = :idTipoPesaje ");
        sql.append("ORDER BY cp.id_cola_pesaje DESC ");
        parameters.put("idDespacho", idDespacho);
        parameters.put("idTipoPesaje", idTipoPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Double obtenerValorPesaje(Integer idDespacho, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        if(idTipoPesaje == 1) 
            sql.append("SELECT valor_pesaje_antes ");
        else
            sql.append("SELECT valor_pesaje_despues ");
        sql.append("FROM sd_despacho ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsDouble(query.getSingleResult());
    }
    
}
