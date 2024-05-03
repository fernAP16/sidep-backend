package com.sidep.proyect.backend.service.implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// import org.springframework.validation.ValidationUtils;

import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.EstadoDespacho;
import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.model.Planta;
import com.sidep.proyect.backend.model.TurnoRevision;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.DespachoService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class DespachoServiceImpl implements DespachoService{

    @Autowired
    private CrudService crudService ;
    // @Autowired
    // private ValidationUtils validationUtils;

    @Override
    public DespachoRegisterOutDto registrarDespacho(DespachoRegisterInDto inDto){
        DespachoRegisterOutDto outDto = new DespachoRegisterOutDto();
        Query query = obtenerDatosValidacion(inDto);
        List<Object[]> resultList = query.getResultList();
        Object[] item = resultList.get(0);

        outDto.setFechaVencLicencia((Date)item[0]);
        outDto.setFechaVencCircTracto((Date)item[1]);
        outDto.setTieneTarjPropiedadTracto(QueryUtils.getAsInteger(item[2]));
        outDto.setFechaVencSoatTracto((Date)item[3]);
        outDto.setFechaVencCircCarreta((Date)item[4]);
        outDto.setTieneTarjPropiedadCarreta(QueryUtils.getAsInteger(item[5]));
        // Validamos las fechas
        Date today = new Date();
        String message = "";
        if(today.compareTo(outDto.getFechaVencLicencia()) >= 0){
            message += "+Licencia de conducir vencido";
        }
        if (today.compareTo(outDto.getFechaVencCircTracto()) >= 0){
            message += "+Tarjeta de circulación del tracto vencida";
        }
        if (outDto.getTieneTarjPropiedadTracto() == 0){
            message += "+Tarjeta de propiedad del tracto no registrada";
        }
        if (today.compareTo(outDto.getFechaVencSoatTracto()) >= 0){
            message += "+No renovó su SOAT\n";
        }
        if (today.compareTo(outDto.getFechaVencCircCarreta()) >= 0){
            message += "+Tarjeta de circulación de la carreta vencida";
        }
        if (outDto.getTieneTarjPropiedadCarreta() == 0){
            message += "+Tarjeta de propiedad de la carreta no registrada";
        } 
        outDto.setErrorMessage(message);
        if(message.equals("")){
            Integer idOrden = registrarNuevaOrden(inDto);
            outDto.setIdDespacho(idOrden);
        }
        return outDto;
    }

    private Query obtenerDatosValidacion(DespachoRegisterInDto inDto){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT co.fecha_venc_licencia, "); // 0
        sql.append("vet.fecha_venc_circulacion as fecha_venc_circ_tracto, "); // 1
        sql.append("vet.tiene_tarjeta_propiedad as tiene_tarj_prop_tracto, "); // 2
        sql.append("vet.fecha_venc_soat as fecha_venc_soat_tracto, "); // 3
        sql.append("vec.fecha_venc_circulacion as fecha_venc_circ_carreta, "); // 4
        sql.append("vec.tiene_tarjeta_propiedad as tiene_tarj_prop_carreta "); // 5
        sql.append("FROM sd_orden_recojo od ");
        sql.append("INNER JOIN sd_conductor co ON co.id_conductor = od.id_conductor ");
        sql.append("INNER JOIN sd_vehiculo vet ON vet.id_vehiculo = od.id_tracto ");
        sql.append("INNER JOIN sd_vehiculo vec ON vec.id_vehiculo = od.id_carreta ");
        sql.append("WHERE od.id_orden_recojo = :idOrden ");
        parameters.put("idOrden", inDto.getIdOrden());

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Integer registrarNuevaOrden (DespachoRegisterInDto inDto){
        Despacho despacho = new Despacho();

        despacho.setOrdenRecojo(new OrdenRecojo());
        despacho.getOrdenRecojo().setIdOrdenRecojo(inDto.getIdOrden());
        despacho.setPlanta(new Planta());
        despacho.getPlanta().setIdPlanta(1);
        despacho.setEstadoDespacho(new EstadoDespacho());
        despacho.getEstadoDespacho().setIdEstadoDespacho(1);
        despacho.setHoraInicioDespacho(new Date());
        despacho.setAuditoria(new Auditoria());
        despacho.getAuditoria().setActivo(1);
        despacho.getAuditoria().setFechaRegistro(new Date());
        despacho.getAuditoria().setUsuarioRegistro("usuario");
        // Falta asignar el turno de revisión

        crudService.create(despacho);

        return despacho.getIdDespacho();
    }
}
