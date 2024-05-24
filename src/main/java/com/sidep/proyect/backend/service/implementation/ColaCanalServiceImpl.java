package com.sidep.proyect.backend.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaRegistrarOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.CanalCarga;
import com.sidep.proyect.backend.model.ColaCanal;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.service.ColaCanalService;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class ColaCanalServiceImpl implements ColaCanalService{

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public ColaCargaRegistrarOutDto registrarTurnoCarga(ColaCargaRegistrarInDto inDto){
        ColaCargaRegistrarOutDto outDto = new ColaCargaRegistrarOutDto();
        Integer idCanalCargaSelected = 0, vehiculosEsperaSelected = 99999;
        Integer idCanalCarga, vehiculosEspera, turnoAsignado;
        String codigoCanalCargaAsignada = "", codigoCanalCarga;

        Query queryCanalesCarga = obtenerCanalesPorPlanta(inDto.getIdPlanta());
        List<Object[]> resultListBanalzas = queryCanalesCarga.getResultList();
        for (Object[] item : resultListBanalzas) {
            idCanalCarga = QueryUtils.getAsInteger(item[0]);
            codigoCanalCarga = QueryUtils.getAsString(item[1]);
            vehiculosEspera = obtenerVehiculosEspera(idCanalCarga);
            if(vehiculosEspera == null)
                vehiculosEspera = 0;
            if(vehiculosEsperaSelected > vehiculosEspera){
                idCanalCargaSelected = idCanalCarga;
                vehiculosEsperaSelected = vehiculosEspera;
                codigoCanalCargaAsignada = codigoCanalCarga;
            }
        }
        turnoAsignado = vehiculosEsperaSelected + 1;
        if(turnoAsignado == 6){
            outDto.setIdColaCargaNuevo(0);
            outDto.setPosicionAsignada(0);
            outDto.setCanalCargaAsignado("");
            return outDto;
        }

        Despacho despacho = entityManager.find(Despacho.class, inDto.getIdDespacho());
        CanalCarga canalCarga = entityManager.find(CanalCarga.class, idCanalCargaSelected);

        ColaCanal colaCanal = new ColaCanal();
        colaCanal.setDespacho(despacho);
        colaCanal.setCanalCarga(canalCarga);
        colaCanal.setPosicion(turnoAsignado);
        colaCanal.setAuditoria(new Auditoria());
        colaCanal.getAuditoria().setActivo(1);
        colaCanal.getAuditoria().setFechaRegistro(new Date());
        colaCanal.getAuditoria().setUsuarioRegistro("usuario");

        crudService.create(colaCanal);

        outDto.setIdColaCargaNuevo(colaCanal.getIdColaCanal());
        outDto.setPosicionAsignada(turnoAsignado);
        outDto.setCanalCargaAsignado(codigoCanalCargaAsignada);

        return outDto;
    }

    private Query obtenerCanalesPorPlanta(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT id_canal_carga, codigo ");
        sql.append("FROM sd_canal_carga ");
        sql.append("WHERE id_planta = :idPlanta ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Integer obtenerVehiculosEspera(Integer idCanalCarga){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) as vehiculos_espera ");
        sql.append("FROM sd_cola_canal ");
        sql.append("WHERE id_canal_carga = :idCanalCarga ");
        sql.append("AND posicion <> 0 ");
        parameters.put("idCanalCarga", idCanalCarga);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        Object result = query.getSingleResult();
        if (result != null) {
            return ((Number) result).intValue();
        } else {
            return 0;
        }
    }

}
