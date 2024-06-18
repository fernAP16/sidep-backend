package com.sidep.proyect.backend.service.implementation;

import java.math.BigDecimal;
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
import com.sidep.proyect.backend.dto.out.DespachoDatosOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.ColaPesaje;
import com.sidep.proyect.backend.model.Despacho;
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

        Query queryBalanzas = obtenerBalanzasPorPlanta(inDto.getIdPlanta(), inDto.getTipoPesaje());
        List<Object[]> resultListBanalzas = queryBalanzas.getResultList();
        for (Object[] item : resultListBanalzas) {
            idZonaBalanza = QueryUtils.getAsInteger(item[0]);
            balanza = QueryUtils.getAsString(item[1]);
            vehiculosEspera = obtenerVehiculosEspera(idZonaBalanza);
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
        ZonaBalanza zonaBalanza = entityManager.find(ZonaBalanza.class, idZonaBalanzaSelected);

        ColaPesaje colaPesajeVacio = new ColaPesaje();
        colaPesajeVacio.setDespacho(despacho);
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

    private Query obtenerBalanzasPorPlanta(Integer idPlanta, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT id_zona_balanza, codigo ");
        sql.append("FROM sd_zona_balanza ");
        sql.append("WHERE id_planta = :idPlanta AND id_tipo_pesaje = :idTipoPesaje");
        parameters.put("idPlanta", idPlanta);
        parameters.put("idTipoPesaje", idTipoPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Integer obtenerVehiculosEspera(Integer idZonaBalanza){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT count(*) as vehiculos_espera ");
        sql.append("FROM sd_cola_pesaje ");
        sql.append("WHERE id_zona_balanza = :idZonaBalanza ");
        sql.append("AND posicion <> 0 ");
        parameters.put("idZonaBalanza", idZonaBalanza);

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
        Double valorVehiculo, limiteInf, limiteSup, pesoUnitario;
        Query query = consultarDatosPesaje(inDto.getIdDespacho(), inDto.getTipoPesaje());

        List<Object[]> resultList = query.getResultList();
        
        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdColaPesaje(QueryUtils.getAsInteger(item[0]));
            outDto.setIdZonaBalanza(QueryUtils.getAsInteger(item[1]));
            outDto.setCodigoZonaBalanza(QueryUtils.getAsString(item[2]));
            outDto.setPosicion(QueryUtils.getAsInteger(item[3]));
        } else {
            outDto.setIdColaPesaje(0);
            outDto.setIdZonaBalanza(0);
            outDto.setCodigoZonaBalanza("");
            outDto.setPosicion(0);
        }

        valorVehiculo = obtenerValorVehiculo(inDto.getIdDespacho());
        outDto.setValorVehiculo(valorVehiculo);

        Query queryValoresPesajes = obtenerValorPesaje(inDto.getIdDespacho());
        List<Object[]> resultListPesajes = queryValoresPesajes.getResultList();
        if (!resultListPesajes.isEmpty()) {
            Object[] itemPesajes = resultListPesajes.get(0);
            if (itemPesajes[0] instanceof BigDecimal) {
                outDto.setValorPesajeVacio(QueryUtils.getAsDouble(((BigDecimal)itemPesajes[0]).doubleValue()));
            } else if (itemPesajes[0] instanceof Integer) {
                outDto.setValorPesajeVacio((Double)((Integer)itemPesajes[0]).doubleValue());
            } else if (itemPesajes[0] instanceof Double) {
                outDto.setValorPesajeVacio((Double)itemPesajes[0]);
            } else {
                outDto.setValorPesajeVacio(0.0);
            }
            
            if (itemPesajes[1] instanceof BigDecimal) {
                outDto.setValorPesajeLleno(QueryUtils.getAsDouble(((BigDecimal)itemPesajes[1]).doubleValue()));
            } else if (itemPesajes[1] instanceof Integer) {
                outDto.setValorPesajeLleno((Double)((Integer)itemPesajes[1]).doubleValue());
            } else if (itemPesajes[1] instanceof Double) {
                outDto.setValorPesajeLleno((Double)itemPesajes[1]);
            } else {
                outDto.setValorPesajeLleno(0.0);
            }
            outDto.setCantidadProductos(QueryUtils.getAsInteger(itemPesajes[2]));
        } else {
            outDto.setValorPesajeVacio(0.0);
            outDto.setValorPesajeLleno(0.0);
        }
        

        limiteInf = obtenerLimitesInferiorDespacho(inDto.getIdDespacho(), inDto.getTipoPesaje());
        outDto.setLimiteInf(limiteInf);

        limiteSup = obtenerLimitesSuperiorDespacho(inDto.getIdDespacho(), inDto.getTipoPesaje());
        outDto.setLimiteSup(limiteSup);

        pesoUnitario = obtenerPesoUnitario(inDto.getIdDespacho());
        outDto.setPesoUnitario(pesoUnitario);

        return outDto;
    }

    private Query consultarDatosPesaje(Integer idDespacho, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        sql.append("SELECT cp.id_cola_pesaje, zb.id_zona_balanza, zb.codigo, cp.posicion ");
        sql.append("FROM sd_zona_balanza zb ");
        sql.append("INNER JOIN sd_cola_pesaje cp ON cp.id_zona_balanza = zb.id_zona_balanza ");
        sql.append("WHERE cp.id_despacho = :idDespacho AND zb.id_tipo_pesaje = :idTipoPesaje ");
        sql.append("ORDER BY cp.id_cola_pesaje DESC ");
        parameters.put("idDespacho", idDespacho);
        parameters.put("idTipoPesaje", idTipoPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Double obtenerValorVehiculo(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        sql.append("SELECT tracto.peso + carreta.peso as peso_vehiculo ");
        sql.append("FROM sd_despacho dp ");
        sql.append("INNER JOIN sd_orden_recojo oc ON oc.id_orden_recojo = dp.id_orden_recojo ");
        sql.append("INNER JOIN sd_vehiculo tracto ON tracto.id_vehiculo = oc.id_tracto ");
        sql.append("INNER JOIN sd_vehiculo carreta ON carreta.id_vehiculo = oc.id_carreta ");
        sql.append("WHERE dp.id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        Object result = query.getSingleResult();
    
        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).doubleValue();
        } else if (result instanceof Double) {
            return (Double) result;
        } else if (result instanceof Integer) {
            return (Double) ((Integer) result).doubleValue();
        } else {
            throw new IllegalArgumentException("Unexpected result type: " + result.getClass().getName());
        }
    }

    private Double obtenerPesoUnitario(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        sql.append("SELECT pv.peso_unidad ");
        sql.append("FROM sd_despacho dp ");
        sql.append("INNER JOIN sd_orden_recojo oc ON oc.id_orden_recojo = dp.id_orden_recojo ");
        sql.append("INNER JOIN sd_producto_venta pv ON pv.id_producto_venta = oc.id_producto_venta ");
        sql.append("WHERE dp.id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        Object result = query.getSingleResult();
    
        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).doubleValue();
        } else if (result instanceof Double) {
            return (Double) result;
        } else if (result instanceof Integer) {
            return (Double) ((Integer) result).doubleValue();
        } else {
            throw new IllegalArgumentException("Unexpected result type: " + result.getClass().getName());
        }
    }

    private Query obtenerValorPesaje(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT dp.valor_pesaje_vacio, dp.valor_pesaje_lleno, oc.cantidad ");
        sql.append("FROM sd_despacho dp ");
        sql.append("INNER JOIN sd_orden_recojo oc ON oc.id_orden_recojo = dp.id_orden_recojo ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);
        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    private Double obtenerLimitesInferiorDespacho(Integer idDespacho, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        if(idTipoPesaje == 1)
            sql.append("SELECT pl.limite_inf_pesaje_antes ");
        else
            sql.append("SELECT pl.limite_inf_pesaje_despues ");
        sql.append("FROM sd_planta pl ");
        sql.append("INNER JOIN sd_despacho dp ON dp.id_planta = pl.id_planta ");
        sql.append("WHERE dp.id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        Object result = query.getSingleResult();
    
        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).doubleValue();
        } else if (result instanceof Double) {
            return (Double) result;
        } else if (result instanceof Integer) {
            return (Double) ((Integer) result).doubleValue();
        } else {
            throw new IllegalArgumentException("Unexpected result type: " + result.getClass().getName());
        }
    }

    private Double obtenerLimitesSuperiorDespacho(Integer idDespacho, Integer idTipoPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        
        if(idTipoPesaje == 1)
            sql.append("SELECT pl.limite_sup_pesaje_antes ");
        else   
        sql.append("SELECT pl.limite_sup_pesaje_despues ");
        sql.append("FROM sd_planta pl ");
        sql.append("INNER JOIN sd_despacho dp ON dp.id_planta = pl.id_planta ");
        sql.append("WHERE dp.id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        Object result = query.getSingleResult();
    
        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).doubleValue();
        } else if (result instanceof Double) {
            return (Double) result;
        } else if (result instanceof Integer) {
            return (Double) ((Integer) result).doubleValue();
        } else {
            throw new IllegalArgumentException("Unexpected result type: " + result.getClass().getName());
        }
    }
    
    @Override
    public DespachoDatosOutDto obtenerDatosDespacho(Integer idColaPesaje){
        DespachoDatosOutDto outDto = new DespachoDatosOutDto();
        Query query = consultarOrdenPorIdColaPesaje(idColaPesaje);
        List<Object[]> result = query.getResultList();
        if(result.size() == 0)return outDto;
        Object[] item = result.get(0);
        outDto.setIdOrdenRecojo(QueryUtils.getAsInteger(item[0]));
        outDto.setRazonSocial(QueryUtils.getAsString(item[1]));
        outDto.setPlacaTracto(QueryUtils.getAsString(item[2]));
        outDto.setPlacaCarreta(QueryUtils.getAsString(item[3]));
        outDto.setProducto(QueryUtils.getAsString(item[4]));
        outDto.setCantidad(QueryUtils.getAsString(item[5]));
        return outDto;
        
    }

    private Query consultarOrdenPorIdColaPesaje(Integer idColaPesaje){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT od.id_orden_recojo, "); // 0
        sql.append("cl.razon_social, "); // 1
        sql.append("ve_tracto.placa as placa_tracto, "); // 2
        sql.append("ve_carreta.placa as placa_carreta, "); // 3
        sql.append("CONCAT(pr.nombre,' ',ma.nombre) as nombre_producto, "); // 4
        sql.append("CONCAT(od.cantidad, ' ' ,un.nombre) as cantidad "); // 5
        sql.append("FROM sd_cola_pesaje cp ");
        sql.append("INNER JOIN sd_despacho de ON de.id_despacho = cp.id_despacho ");
        sql.append("INNER JOIN sd_orden_recojo od ON od.id_orden_recojo = de.id_orden_recojo ");
        sql.append("INNER JOIN sd_sede_cliente sc ON sc.id_sede_cliente = od.id_sede_cliente ");
        sql.append("INNER JOIN sd_cliente cl ON cl.id_cliente = sc.id_cliente ");
        sql.append("INNER JOIN sd_vehiculo ve_tracto ON od.id_tracto = ve_tracto.id_vehiculo ");
        sql.append("INNER JOIN sd_vehiculo ve_carreta ON od.id_carreta = ve_carreta.id_vehiculo ");
        sql.append("INNER JOIN sd_producto_venta pv ON od.id_producto_venta = pv.id_producto_venta ");
        sql.append("INNER JOIN sd_producto pr ON pr.id_producto = pv.id_producto ");
        sql.append("INNER JOIN sd_marca ma ON ma.id_marca = pv.id_marca ");
        sql.append("INNER JOIN sd_unidad un ON un.id_unidad = pv.id_unidad ");
        sql.append("WHERE cp.id_cola_pesaje = :idColaPesaje ");

        parameters.put("idColaPesaje", idColaPesaje);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }
}
