package com.sidep.proyect.backend.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.ColaCanalFinalizarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaRegistrarInDto;
import com.sidep.proyect.backend.dto.in.ColaCargaVerificarInDto;
import com.sidep.proyect.backend.dto.in.DespachoActualizarEstadoInDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDatosOutDto;
import com.sidep.proyect.backend.dto.out.ColaCargaDespachoOutDto;
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

    @Override
    public ColaCargaDatosOutDto obtenerDatosColaCarga(Integer idDespacho){
        ColaCargaDatosOutDto outDto = new ColaCargaDatosOutDto();
        Query query = consultarDatosColaCarga(idDespacho);

        List<Object[]> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdColaCanal(QueryUtils.getAsInteger(item[0]));
            outDto.setIdCanalCarga(QueryUtils.getAsInteger(item[1]));
            outDto.setCodigoCanalCarga(QueryUtils.getAsString(item[2]));
            outDto.setPosicionActual(QueryUtils.getAsInteger(item[3]));
        } else {
            outDto.setIdColaCanal(0);
            outDto.setIdCanalCarga(0);
            outDto.setCodigoCanalCarga("");
            outDto.setPosicionActual(-1);
        }
        return outDto;
    }

    private Query consultarDatosColaCarga(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT cc.id_cola_canal, cc.id_canal_carga, ca.codigo, cc.posicion ");
        sql.append("FROM sd_cola_canal cc ");
        sql.append("INNER JOIN sd_canal_carga ca ON ca.id_canal_carga = cc.id_canal_carga ");
        sql.append("WHERE cc.id_despacho = :idDespacho ");
        sql.append("ORDER BY cc.id_cola_canal DESC ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;

    }

    @Override
    public ColaCargaDespachoOutDto obtenerDatosCompletosCarga(Integer idDespacho){
        ColaCargaDespachoOutDto outDto = new ColaCargaDespachoOutDto();
        Integer idColaCanal = 0;
        Query query, queryIdColaCanal;
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        queryIdColaCanal = consultarDatosColaCarga(idDespacho);

        List<Object[]> resultListIdColaCanal = queryIdColaCanal.getResultList();

        if (resultListIdColaCanal.isEmpty()) {
            outDto.setIdOrdenRecojo(-1);
            return outDto;
        }

        Object[] itemIdColaCanal = resultListIdColaCanal.get(0);
        idColaCanal = QueryUtils.getAsInteger(itemIdColaCanal[0]);
        outDto.setIdColaCanal(idColaCanal);
        outDto.setIdCanalCarga(QueryUtils.getAsInteger(itemIdColaCanal[1]));
        outDto.setCodigoCanalCarga(QueryUtils.getAsString(itemIdColaCanal[2]));

        query = consultarOrdenPorIdColaCanal(idColaCanal);
        List<Object[]> result = query.getResultList();
        if(result.size() == 0){
            outDto.setIdOrdenRecojo(0);
            return outDto;
        }
        Object[] item = result.get(0);
        outDto.setIdOrdenRecojo(QueryUtils.getAsInteger(item[0]));
        outDto.setRazonSocial(QueryUtils.getAsString(item[1]));
        outDto.setProducto(QueryUtils.getAsString(item[2]));
        outDto.setCantidad(QueryUtils.getAsString(item[3]));

        try{
            String horaInicioString = QueryUtils.getAsString((Date)item[4]);
            if(horaInicioString == null){
                outDto.setHoraInicio(null);
            } else {
                outDto.setHoraInicio(sdfTime.parse(horaInicioString));
            }
            String horaFinString = QueryUtils.getAsString((Date)item[5]);
            if(horaFinString == null){
                outDto.setHoraFin(null);
            } else {
                outDto.setHoraFin(sdfTime.parse(horaFinString));
            }
        } catch (ParseException e){
            System.out.println(e);
        }
        return outDto;
        
    }

    private Query consultarOrdenPorIdColaCanal(Integer idColaCanal){
        
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT od.id_orden_recojo, "); // 0
        sql.append("cl.razon_social, "); // 1
        sql.append("CONCAT(pr.nombre,' ',ma.nombre) as nombre_producto, "); // 2
        sql.append("CONCAT(od.cantidad, ' ' ,un.nombre) as cantidad, "); // 3
        sql.append("de.hora_inicio_carga, "); // 4
        sql.append("de.hora_fin_carga "); // 5
        sql.append("FROM sd_cola_canal cc ");
        sql.append("INNER JOIN sd_despacho de ON de.id_despacho = cc.id_despacho ");
        sql.append("INNER JOIN sd_orden_recojo od ON od.id_orden_recojo = de.id_orden_recojo ");
        sql.append("INNER JOIN sd_sede_cliente sc ON sc.id_sede_cliente = od.id_sede_cliente ");
        sql.append("INNER JOIN sd_cliente cl ON cl.id_cliente = sc.id_cliente ");
        sql.append("INNER JOIN sd_producto_venta pv ON od.id_producto_venta = pv.id_producto_venta ");
        sql.append("INNER JOIN sd_producto pr ON pr.id_producto = pv.id_producto ");
        sql.append("INNER JOIN sd_marca ma ON ma.id_marca = pv.id_marca ");
        sql.append("INNER JOIN sd_unidad un ON un.id_unidad = pv.id_unidad ");
        sql.append("WHERE cc.id_cola_canal = :idColaCanal ");

        parameters.put("idColaCanal", idColaCanal);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public Integer verificarCanalCarga(ColaCargaVerificarInDto inDto){
        String contrasena = obtenerClaveCanalCarga(inDto.getIdCanalCarga());
        if(contrasena.equals(inDto.getQrLeido())) return 1;
        return 0;
    }

    private String obtenerClaveCanalCarga(Integer idCanalCarga){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT qr_fisico ");
        sql.append("FROM sd_canal_carga ");
        sql.append("WHERE id_canal_carga = :idCanalCarga ");
        parameters.put("idCanalCarga", idCanalCarga);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsString(query.getSingleResult());
    }
    
    @Override
    public Integer finalizarCarga(ColaCanalFinalizarInDto idDto){
        Query queryFinalizar = actualizarFinalizarDespacho(idDto.getIdDespacho());
        int filas = queryFinalizar.executeUpdate();
        if(filas > 0){
            Query queryBajar = bajarUnaPosicion(idDto.getIdColaCanal());
            int filasBajas = queryBajar.executeUpdate();
            if(filasBajas > 0){
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private Query actualizarFinalizarDespacho(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_despacho ");
        sql.append("SET hora_fin_carga = sysdate() ");
        sql.append("WHERE id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    private Query bajarUnaPosicion(Integer idCanalCarga){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_cola_canal ");
        sql.append("SET posicion = posicion - 1 ");
        sql.append("WHERE id_canal_carga = :idCanalCarga ");
        sql.append("AND posicion != 0 ");
        parameters.put("idCanalCarga", idCanalCarga);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }
}
