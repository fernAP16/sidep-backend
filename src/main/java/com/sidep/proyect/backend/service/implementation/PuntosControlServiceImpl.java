package com.sidep.proyect.backend.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.CoordenadasPlantaInDto;
import com.sidep.proyect.backend.dto.out.PuntosControlPlantaOutDto;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.PuntosControlService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class PuntosControlServiceImpl implements PuntosControlService {
    
    @Autowired
    private CrudService crudService ;

    @Override
    public List<PuntosControlPlantaOutDto> listarPuntosControlPorPlanta(CoordenadasPlantaInDto inDto){
        List<PuntosControlPlantaOutDto> outDto = new ArrayList<>();

        Query queryPlanta = obtenerPlanta(inDto.getX(), inDto.getY());
        Object itemPlanta = queryPlanta.getSingleResult();
        Integer idPlanta = QueryUtils.getAsInteger(itemPlanta);

        Query query = consultarPuntosControlPorPlanta(idPlanta);
        List<Object[]> resultList = query.getResultList();
        for(int i=0; i<resultList.size(); i++){
            Object[] item = resultList.get(i);
            PuntosControlPlantaOutDto out = new PuntosControlPlantaOutDto();
            out.setIdPuntoControl(QueryUtils.getAsInteger(item[0]));
            out.setCodigoPuntoControl(QueryUtils.getAsString(item[1]));
            
            Query queryPuntoControl = consultarDatosRevision(QueryUtils.getAsInteger(item[0]));
            List<Object[]> resultPuntosControl = queryPuntoControl.getResultList();
            if(resultPuntosControl.size() == 0){
                out.setIdRevisorAsignado(null);
                out.setIdTurnoRevision(null);
            } else {
                Object[] itemDatos = resultPuntosControl.get(0);

                out.setIdRevisorAsignado(QueryUtils.getAsInteger(itemDatos[0]));
                out.setIdTurnoRevision(QueryUtils.getAsInteger(itemDatos[1]));
            }
            outDto.add(out);
        }
        return outDto;

    }

    private Query obtenerPlanta(Double x, Double y){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT id_planta ");
        sql.append("FROM sd_planta ");
        sql.append("WHERE ubicacion_x1 < :x AND ubicacion_x2 > :x ");
        sql.append("AND ubicacion_y1 < :y AND ubicacion_y2 > :y ");
        parameters.put("x", x);
        parameters.put("y", y);
        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Query consultarPuntosControlPorPlanta(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT pc.id_punto_control, "); // 0
        sql.append("pc.codigo "); // 1
        sql.append("FROM sd_punto_control pc ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = pc.id_planta ");
        sql.append("WHERE pc.id_planta = :idPlanta ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Query consultarDatosRevision(Integer idPuntoControl){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT id_turno_revision, "); // 0
        sql.append("id_revisor "); // 1
        sql.append("FROM sd_turno_revision ");
        sql.append("WHERE id_punto_control = :idPuntoControl ");
        sql.append("AND ((es_aprobado IS NULL AND id_revisor IS NOT NULL) OR (es_aprobado IS NOT NULL AND hora_fin IS NULL))");
        sql.append("ORDER BY id_turno_revision DESC");
        parameters.put("idPuntoControl", idPuntoControl);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

}
