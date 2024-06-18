package com.sidep.proyect.backend.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.DespachoActualizarEstadoInDto;
import com.sidep.proyect.backend.dto.in.DespachoNuevoPesajeInDto;
import com.sidep.proyect.backend.dto.in.DespachoRegisterInDto;
import com.sidep.proyect.backend.dto.in.SalidaDespachoInDto;
import com.sidep.proyect.backend.dto.out.DespachoObtenerVigenteOutDto;
import com.sidep.proyect.backend.dto.out.DespachoPorOrdenOutDto;
import com.sidep.proyect.backend.dto.out.DespachoRegisterOutDto;
import com.sidep.proyect.backend.dto.out.DespachoTerminadoOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.EstadoDespacho;
import com.sidep.proyect.backend.model.OrdenRecojo;
import com.sidep.proyect.backend.model.Planta;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.DespachoService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class DespachoServiceImpl implements DespachoService{

    @Autowired
    private CrudService crudService ;

    @Override
    public DespachoRegisterOutDto registrarDespacho(DespachoRegisterInDto inDto) throws ParseException {
        DespachoRegisterOutDto outDto = new DespachoRegisterOutDto();
        Query query = obtenerDatosValidacion(inDto);
        List<Object[]> resultList = query.getResultList();
        
        if (resultList.isEmpty()) {
            outDto.setErrorMessage("+No se encontraron datos de validación");
            return outDto;
        }
        
        Object[] item = resultList.get(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Query queryPlanta = obtenerPlanta(inDto.getX(), inDto.getY());
        List<Object> plantas = queryPlanta.getResultList();
        
        if (plantas.isEmpty()) {
            outDto.setErrorMessage("+No se encuentra cerca a la planta de despacho");
            return outDto;
        }
        
        Object itemPlanta = plantas.get(0);
        Integer idPlanta = QueryUtils.getAsInteger(itemPlanta);
        
        if (idPlanta == null) {
            outDto.setErrorMessage("+No se encuentra cerca a la planta de despacho");
            return outDto;
        }
        
        outDto.setIdPlanta(idPlanta);

        try {
            outDto.setFechaVencLicencia(sdf.parse(QueryUtils.getAsString(item[0])));
            outDto.setFechaVencCircTracto(sdf.parse(QueryUtils.getAsString(item[1])));
            outDto.setTieneTarjPropiedadTracto(QueryUtils.getAsInteger(item[2]));
            outDto.setFechaVencSoatTracto(sdf.parse(QueryUtils.getAsString((Date)item[3])));
            outDto.setFechaVencCircCarreta(sdf.parse(QueryUtils.getAsString((Date)item[4])));
            outDto.setTieneTarjPropiedadCarreta(QueryUtils.getAsInteger(item[5]));
        } catch (ParseException e) {
            System.out.println(e);
        }

        outDto.setErrorMessage(obtenerMensajeError(outDto));

        if (outDto.getErrorMessage().isEmpty()) {
            // Registrar despacho
            Integer idDespacho = registrarNuevoDespacho(inDto.getIdOrden(), idPlanta);
            outDto.setIdDespacho(idDespacho);
            // Actualizar el estado de la orden a "Despachando"
            Query queryOrden = actualizarOrden(inDto.getIdOrden());
            int filasActualizadas = queryOrden.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Se actualizaron " + filasActualizadas + " filas.");
            } else {
                System.out.println("No se realizaron actualizaciones.");
            }
        }
        return outDto;
    }

    private Query obtenerDatosValidacion(DespachoRegisterInDto inDto) {
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

    private String obtenerMensajeError(DespachoRegisterOutDto outDto) {
        Date today = new Date();
        String message = "";
        if (today.compareTo(outDto.getFechaVencLicencia()) >= 0) {
            message += "+Licencia de conducir vencido";
        }
        if (today.compareTo(outDto.getFechaVencCircTracto()) >= 0) {
            message += "+Tarjeta de circulación del tracto vencida";
        }
        if (outDto.getTieneTarjPropiedadTracto() == 0) {
            message += "+Tarjeta de propiedad del tracto no registrada";
        }
        if (today.compareTo(outDto.getFechaVencSoatTracto()) >= 0) {
            message += "+No renovó su SOAT\n";
        }
        if (today.compareTo(outDto.getFechaVencCircCarreta()) >= 0) {
            message += "+Tarjeta de circulación de la carreta vencida";
        }
        if (outDto.getTieneTarjPropiedadCarreta() == 0) {
            message += "+Tarjeta de propiedad de la carreta no registrada";
        }
        return message;
    }

    private Query obtenerPlanta(Double x, Double y) {
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


    private Integer registrarNuevoDespacho (Integer idOrden, Integer idPlanta){
        Despacho despacho = new Despacho();

        despacho.setOrdenRecojo(new OrdenRecojo());
        despacho.getOrdenRecojo().setIdOrdenRecojo(idOrden);
        despacho.setPlanta(new Planta());
        despacho.getPlanta().setIdPlanta(idPlanta);
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
        sql.append("ORDER BY id_despacho DESC ");
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

    @Override
    public DespachoTerminadoOutDto obtenerDatosDespachoTerminado(Integer idOrden) throws ParseException {
        DespachoTerminadoOutDto outDto = new DespachoTerminadoOutDto();
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Query query = consultarDatosDespachoTerminado(idOrden);
        List<Object[]> resultList = query.getResultList();
        
        if (resultList.isEmpty()) {
            return outDto;
        }
        
        Object[] item = resultList.get(0);

        try {
            outDto.setFechaRecojo(sdfFecha.parse(QueryUtils.getAsString(item[0])));
            outDto.setHoraLlegada(sdfHora.parse(QueryUtils.getAsString(item[0])));
            outDto.setHoraSalida(sdfHora.parse(QueryUtils.getAsString(item[1])));
        } catch (ParseException e) {
            System.out.println(e);
        }

        return outDto;
    }

    private Query consultarDatosDespachoTerminado(Integer idOrden){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT hora_inicio_despacho, hora_fin_despacho ");
        sql.append("FROM sd_despacho ");
        sql.append("WHERE id_orden_recojo = :idOrden ");
        sql.append("ORDER BY id_despacho DESC; ");
        parameters.put("idOrden", idOrden);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public Integer actualizarEstadoDespacho(DespachoActualizarEstadoInDto inDto){
        Query queryEstadoPesaje = actualizarDespachoEstado(inDto.getIdDespacho(), inDto.getIdNuevoEstado());
        int filasActualizadasEstado = queryEstadoPesaje.executeUpdate();
        if(filasActualizadasEstado > 0){
            if(inDto.getIdNuevoEstado() == 3 || inDto.getIdNuevoEstado() == 5){
                return actualizarOrden(inDto.getIdDespacho(), 1);
            } else if(inDto.getIdNuevoEstado() == 11){
                return actualizarOrden(inDto.getIdDespacho(), 3);
            }
            return 1;
        } else {
            return 0;
        }
    }

    private Query actualizarDespachoEstado(Integer idDespacho, Integer idNuevoEstado){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_despacho ");
        sql.append("SET id_estado_despacho = :idNuevoEstado ");
        if(idNuevoEstado == 7)sql.append(", hora_inicio_carga = sysdate() ");
        else if(idNuevoEstado == 11)sql.append(", hora_fin_despacho = sysdate() ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idNuevoEstado", idNuevoEstado);
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Integer actualizarOrden(Integer idDespacho, Integer valor){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_orden_recojo ");
        sql.append("SET id_estado_orden = :valor ");
        sql.append("WHERE id_orden_recojo in (SELECT id_orden_recojo FROM sd_despacho WHERE id_despacho = :idDespacho) ");
        parameters.put("valor", valor);
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        int rowsUpdated = query.executeUpdate();
        return rowsUpdated;
    }

    @Override
    public Integer actualizarValorPesaje(DespachoNuevoPesajeInDto inDto){
        Query queryEstadoPesaje = modificarPesaje(inDto);
        int filasActualizadasEstado = queryEstadoPesaje.executeUpdate();
        if(filasActualizadasEstado > 0){
            Query queryBajarPosiciones  = bajarUnaPosicion(inDto.getIdZonaBalanza());
            int filasBajarPosiciones = queryBajarPosiciones.executeUpdate();
            if(filasBajarPosiciones > 0){
                return 1;
            }
            return 0;
        } else {
            return 0;
        }
    }

    private Query modificarPesaje(DespachoNuevoPesajeInDto inDto){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_despacho ");
        if(inDto.getTipoPesaje() == 1)
            sql.append("SET valor_pesaje_vacio = :valorPesaje ");
        else
            sql.append("SET valor_pesaje_lleno = :valorPesaje ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("valorPesaje", inDto.getValorPesaje());
        parameters.put("idDespacho", inDto.getIdDespacho());

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Query bajarUnaPosicion(Integer inZonaBalanza){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_cola_pesaje ");
        sql.append("SET posicion = posicion - 1 ");
        sql.append("WHERE id_zona_balanza = :inZonaBalanza ");
        sql.append("AND posicion != 0 ");
        parameters.put("inZonaBalanza", inZonaBalanza);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public Date registrarSalidaDespacho(SalidaDespachoInDto inDto)  throws ParseException{
        Integer comprobarPlanta = consultarQrSalidaPlanta(inDto.getIdPlanta(), inDto.getQrSalida());
        if(comprobarPlanta != null){
            Query queryEstadoPesaje = actualizarHoraSalida(inDto.getIdDespacho());
            int filasActualizadasEstado = queryEstadoPesaje.executeUpdate();
            if(filasActualizadasEstado > 0){
                Query queryHoraSalida = consultarHoraSalida(inDto.getIdDespacho());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return sdf.parse(QueryUtils.getAsString((Date) queryHoraSalida.getSingleResult()));
                } catch (ParseException e){
                    System.out.println(e);
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private Integer consultarQrSalidaPlanta(Integer idPlanta, String qrSalida){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT activo ");
        sql.append("FROM sd_planta ");
        sql.append("WHERE id_planta = :idPlanta AND qr_salida = :qrSalida ");
        parameters.put("idPlanta", idPlanta);
        parameters.put("qrSalida", qrSalida);
        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        try {
            return QueryUtils.getAsInteger(query.getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    private Query actualizarHoraSalida(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_despacho ");
        sql.append("SET hora_fin_despacho = sysdate() ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Query consultarHoraSalida(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT hora_fin_despacho ");
        sql.append("FROM sd_despacho ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);
        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }
}
