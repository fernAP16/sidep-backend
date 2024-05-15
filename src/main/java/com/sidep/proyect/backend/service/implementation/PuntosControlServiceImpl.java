package com.sidep.proyect.backend.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<PuntosControlPlantaOutDto> listarPuntosControlPorPlanta(Integer idPlanta){
        List<PuntosControlPlantaOutDto> outDto = new ArrayList<>();
        Query query = consultarPuntosControlPorPlanta(idPlanta);
        List<Object[]> resultList = query.getResultList();
        for(int i=0; i<resultList.size(); i++){
            Object[] item = resultList.get(i);
            PuntosControlPlantaOutDto out = new PuntosControlPlantaOutDto();
            out.setIdPuntoControl(QueryUtils.getAsInteger(item[0]));
            out.setCodigoPuntoControl(QueryUtils.getAsString(item[1]));
            out.setDisponible(QueryUtils.getAsInteger(item[2]));
            outDto.add(out);
        }
        return outDto;

    }

    private Query consultarPuntosControlPorPlanta(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT pc.id_punto_control, "); // 0
        sql.append("pc.codigo, "); // 1
        sql.append("tr.id_revisor "); // 2
        sql.append("FROM sd_punto_control pc ");
        sql.append("LEFT JOIN sd_turno_revision tr ON tr.id_punto_control = pc.id_punto_control ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = pc.id_planta ");
        sql.append("WHERE pc.id_planta = :idPlanta ");

        parameters.put("idPlanta", idPlanta);
        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

}
