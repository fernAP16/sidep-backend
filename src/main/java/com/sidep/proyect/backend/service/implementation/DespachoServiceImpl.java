package com.sidep.proyect.backend.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.out.DespachoObtenerVigenteOutDto;
import com.sidep.proyect.backend.dto.out.DespachoPorOrdenOutDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.EstadoDespacho;
import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.model.Planta;
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

    @Override
    public DespachoRegisterOutDto registrarDespacho (DespachoRegisterInDto inDto) throws ParseException{
        DespachoRegisterOutDto outDto = new DespachoRegisterOutDto();
        Query query = obtenerDatosValidacion(inDto);
        List<Object[]> resultList = query.getResultList();
        Object[] item = resultList.get(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            outDto.setFechaVencLicencia(sdf.parse(QueryUtils.getAsString(item[0])));
            outDto.setFechaVencCircTracto(sdf.parse(QueryUtils.getAsString(item[1])));
            outDto.setTieneTarjPropiedadTracto(QueryUtils.getAsInteger(item[2]));
            outDto.setFechaVencSoatTracto(sdf.parse(QueryUtils.getAsString((Date)item[3])));
            outDto.setFechaVencCircCarreta(sdf.parse(QueryUtils.getAsString((Date)item[4])));
            outDto.setTieneTarjPropiedadCarreta(QueryUtils.getAsInteger(item[5]));
        } catch (ParseException e){
            System.out.println(e);
        }

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
            // Registrar orden
            Integer idDespacho = registrarNuevoDespacho(inDto);
            outDto.setIdDespacho(idDespacho);
            // Obtener el idPlanta
            outDto.setIdPlanta(1);
            // Actualizar el estado de la orden a "Despachando"
            System.out.println(inDto.getIdOrden());
            Query queryOrden = actualizarOrden(inDto.getIdOrden());
            int filasActualizadas = queryOrden.executeUpdate();
            if(filasActualizadas > 0){
                System.out.println("Se actualizaron " + filasActualizadas + " filas.");
            } else {
                System.out.println("No se realizaron actualizaciones.");
            }
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

    private Integer registrarNuevoDespacho (DespachoRegisterInDto inDto){
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
        crudService.create(despacho);

        return despacho.getIdDespacho();
    }

    private Query actualizarOrden(Integer idOrdenDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_orden_recojo ");
        sql.append("SET id_estado_orden = 2 ");
        sql.append("WHERE id_orden_recojo = :idOrdenDespacho ");
        parameters.put("idOrdenDespacho", idOrdenDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public DespachoObtenerVigenteOutDto obtenerDespachoVigentePorConductor(Integer idConductor){
        DespachoObtenerVigenteOutDto outDto = new DespachoObtenerVigenteOutDto();
        Query query = consultarDespachoVigente(idConductor);

        List<Object[]> resultList = query.getResultList();
        
        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdEstadoOrden(QueryUtils.getAsInteger(item[0]));
            outDto.setIdDespacho(QueryUtils.getAsInteger(item[1]));
            outDto.setIdPlanta(QueryUtils.getAsInteger(item[2]));
        } else {
            outDto.setIdEstadoOrden(0);
            outDto.setIdDespacho(0);
            outDto.setIdPlanta(0);
        }

        return outDto;
    }

    private Query consultarDespachoVigente(Integer idConductor){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT ed.id_estado_despacho, MAX(de.id_despacho) AS id_despacho, de.id_planta ");
        sql.append("FROM sd_estado_despacho ed ");
        sql.append("INNER JOIN sd_despacho de ON ed.id_estado_despacho = de.id_estado_despacho ");
        sql.append("INNER JOIN sd_orden_recojo od ON de.id_orden_recojo = od.id_orden_recojo ");
        sql.append("WHERE od.id_conductor = :idConductor ");
        sql.append("GROUP BY ed.id_estado_despacho, de.id_planta ");
        parameters.put("idConductor", idConductor);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public DespachoPorOrdenOutDto obtenerUltimoDespachoPorOrden(Integer idOrden){
        DespachoPorOrdenOutDto outDto = new DespachoPorOrdenOutDto();
        Query query = consultarUltimoDespachoPorOden(idOrden);
        Integer idDespacho = QueryUtils.getAsInteger(query.getSingleResult());
        outDto.setIdDespacho(idDespacho);
        return outDto;
    }

    private Query consultarUltimoDespachoPorOden(Integer idOrden){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(id_despacho) as id_despacho ");
        sql.append("FROM sd_despacho ");
        sql.append("WHERE id_orden_recojo = :idOrden ");
        parameters.put("idOrden", idOrden);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }
}
