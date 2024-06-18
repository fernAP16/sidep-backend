package com.sidep.proyect.backend.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.dto.in.TurnoRevisionAsignarInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionInDto;
import com.sidep.proyect.backend.dto.in.TurnoRevisionIncidenciaInDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionAsignarOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionConductorOutDto;
import com.sidep.proyect.backend.dto.out.DespachoDatosOutDto;
import com.sidep.proyect.backend.dto.out.TurnoRevisionOutDto;
import com.sidep.proyect.backend.model.Auditoria;
import com.sidep.proyect.backend.model.Despacho;
import com.sidep.proyect.backend.model.Incidencia;
import com.sidep.proyect.backend.model.TurnoIncidencia;
import com.sidep.proyect.backend.model.TurnoRevision;
import com.sidep.proyect.backend.service.CrudService;
import com.sidep.proyect.backend.service.TurnoRevisionService;
import com.sidep.proyect.backend.util.QueryUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class TurnoRevisionServiceImpl implements TurnoRevisionService{

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public TurnoRevisionOutDto registrarTurnoRevision(TurnoRevisionInDto inDto) {

        TurnoRevisionOutDto outDto = new TurnoRevisionOutDto();
        TurnoRevision turnoRevision = new TurnoRevision();

        Query queryPlanta = obtenerPlanta(inDto.getX(), inDto.getY());
        Object itemPlanta = queryPlanta.getSingleResult();
        Integer idPlanta = QueryUtils.getAsInteger(itemPlanta);

        Integer turnoAsignado = obtenerTurnoAsignado(idPlanta);
        Integer turnoActual = obtenerTurnoActual(idPlanta);
        if(turnoAsignado == null) turnoAsignado = 0;
        turnoAsignado += 1;
        if(turnoActual == null) turnoActual = 0;
        turnoActual += 1;
        turnoRevision.setDespacho(new Despacho());
        turnoRevision.getDespacho().setIdDespacho(inDto.getIdDespacho());
        turnoRevision.setTurnoDia(turnoAsignado);
        turnoRevision.setAuditoria(new Auditoria());
        turnoRevision.getAuditoria().setActivo(1);
        turnoRevision.getAuditoria().setFechaRegistro(new Date());
        turnoRevision.getAuditoria().setUsuarioRegistro("usuario");

        crudService.create(turnoRevision);

        outDto.setIdTurnoRevision(turnoRevision.getIdTurnoRevision());
        outDto.setTurnoActual(turnoActual);
        outDto.setTurnoAsignado(turnoAsignado);

        return outDto;
    }

    private Integer obtenerTurnoAsignado(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(tr.turno_dia) as ultimo_turno FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho dp ON tr.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON dp.id_planta = pl.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta AND DATE(tr.fecha_registro) = CURDATE() ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    private Integer obtenerTurnoActual(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MAX(tr.turno_dia) as turno_actual ");
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho dp ON tr.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = dp.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta ");
        sql.append("AND DATE(tr.fecha_registro) = CURDATE() ");
        sql.append("AND tr.hora_inicio IS NOT NULL ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    @Override
    public TurnoRevisionOutDto obtenerTurnoRevision(TurnoRevisionInDto inDto){
        TurnoRevisionOutDto outDto = new TurnoRevisionOutDto();

        Query queryPlanta = obtenerPlanta(inDto.getX(), inDto.getY());
        Object itemPlanta = queryPlanta.getSingleResult();
        Integer idPlanta = QueryUtils.getAsInteger(itemPlanta);

        Integer turnoActual = obtenerTurnoActual(idPlanta);
        Query query = consultarTurnoAsignado(inDto.getIdDespacho());
        List<Object[]> resultList = query.getResultList();
        if(turnoActual == null) turnoActual = 1;
        if (!resultList.isEmpty()) {
            Object[] item = resultList.get(0);
            outDto.setIdTurnoRevision(QueryUtils.getAsInteger(item[0]));
            outDto.setTurnoAsignado(QueryUtils.getAsInteger(item[1]));
            outDto.setTurnoActual(turnoActual);
        } else {
            outDto.setIdTurnoRevision(0);
            outDto.setTurnoAsignado(0);
            outDto.setTurnoActual(0);
        }

        return outDto;
    }

    private Query consultarTurnoAsignado(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT tr.id_turno_revision, tr.turno_dia ");
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho ds ON tr.id_despacho = ds.id_despacho ");
        sql.append("WHERE ds.id_despacho = :idDespacho ");

        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public DespachoDatosOutDto obtenerDatosDespacho(Integer idTurnoRevision){
        DespachoDatosOutDto outDto = new DespachoDatosOutDto();
        Query query = consultarOrdenPorIdRevision(idTurnoRevision);
        List<Object[]> result = query.getResultList();
        Object[] item = result.get(0);
        outDto.setIdOrdenRecojo(QueryUtils.getAsInteger(item[0]));
        outDto.setRazonSocial(QueryUtils.getAsString(item[1]));
        outDto.setPlacaTracto(QueryUtils.getAsString(item[2]));
        outDto.setPlacaCarreta(QueryUtils.getAsString(item[3]));
        outDto.setProducto(QueryUtils.getAsString(item[4]));
        outDto.setCantidad(QueryUtils.getAsString(item[5]));
        return outDto;
    }

    private Query consultarOrdenPorIdRevision(Integer idTurnoRevision){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT od.id_orden_recojo, "); // 0
        sql.append("cl.razon_social, "); // 1
        sql.append("ve_tracto.placa as placa_tracto, "); // 2
        sql.append("ve_carreta.placa as placa_carreta, "); // 3
        sql.append("CONCAT(pr.nombre,' ',ma.nombre) as nombre_producto, "); // 4
        sql.append("CONCAT(od.cantidad, ' ' ,un.nombre) as cantidad "); // 5
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho de ON de.id_despacho = tr.id_despacho ");
        sql.append("INNER JOIN sd_orden_recojo od ON od.id_orden_recojo = de.id_orden_recojo ");
        sql.append("INNER JOIN sd_sede_cliente sc ON sc.id_sede_cliente = od.id_sede_cliente ");
        sql.append("INNER JOIN sd_cliente cl ON cl.id_cliente = sc.id_cliente ");
        sql.append("INNER JOIN sd_vehiculo ve_tracto ON od.id_tracto = ve_tracto.id_vehiculo ");
        sql.append("INNER JOIN sd_vehiculo ve_carreta ON od.id_carreta = ve_carreta.id_vehiculo ");
        sql.append("INNER JOIN sd_producto_venta pv ON od.id_producto_venta = pv.id_producto_venta ");
        sql.append("INNER JOIN sd_producto pr ON pr.id_producto = pv.id_producto ");
        sql.append("INNER JOIN sd_marca ma ON ma.id_marca = pv.id_marca ");
        sql.append("INNER JOIN sd_unidad un ON un.id_unidad = pv.id_unidad ");
        sql.append("WHERE tr.id_turno_revision = :idTurnoRevision ");

        parameters.put("idTurnoRevision", idTurnoRevision);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public TurnoRevisionAsignarOutDto asignarRevisorYPuntoControlATurnoRevision(TurnoRevisionAsignarInDto inDto){
        TurnoRevisionAsignarOutDto outDto = new TurnoRevisionAsignarOutDto();

        Query queryPlanta = obtenerPlanta(inDto.getX(), inDto.getY());
        Object itemPlanta = queryPlanta.getSingleResult();
        Integer idPlanta = QueryUtils.getAsInteger(itemPlanta);

        Integer idTurnoRevisionSiguienteTurno = siguienteTurnoRegistro(idPlanta);
        Query queryOrden = actualizarTurnoRevision(inDto, idTurnoRevisionSiguienteTurno);
        int filasActualizadas = queryOrden.executeUpdate();
        if(filasActualizadas > 0){
            System.out.println("Se actualizaron " + filasActualizadas + " filas.");
        } else {
            System.out.println("No se realizaron actualizaciones.");
        }
        outDto.setIdTurnoRevision(idTurnoRevisionSiguienteTurno);
        outDto.setIdPlanta(idPlanta);
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

    private Integer siguienteTurnoRegistro(Integer idPlanta){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();
        sql.append("SELECT MIN(id_turno_revision) as siguiente_turno ");
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_despacho dp ON tr.id_despacho = dp.id_despacho ");
        sql.append("INNER JOIN sd_planta pl ON pl.id_planta = dp.id_planta ");
        sql.append("WHERE dp.id_planta = :idPlanta ");
        sql.append("AND DATE(tr.fecha_registro) = CURDATE() ");
        sql.append("AND tr.hora_inicio IS NULL ");
        parameters.put("idPlanta", idPlanta);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return QueryUtils.getAsInteger(query.getSingleResult());
    }

    private Query actualizarTurnoRevision(TurnoRevisionAsignarInDto inDto, Integer idTurnoRevisionSiguienteTurno){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_turno_revision ");
        sql.append("SET id_revisor = :idRevisor, id_punto_control = :idPuntoControl, hora_inicio=sysdate() ");
        sql.append("WHERE id_turno_revision = :idTurnoRevision ");
        parameters.put("idRevisor", inDto.getIdRevisor());
        parameters.put("idPuntoControl", inDto.getIdPuntoControl());
        parameters.put("idTurnoRevision", idTurnoRevisionSiguienteTurno);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public Integer aprobarRevision(Integer idTurnoRevision){
        Query queryAprobado = actualizarRevisionAprobado(idTurnoRevision);
        int filasActualizadas = queryAprobado.executeUpdate();
        return filasActualizadas > 0 ? 1 : 0;
    }

    private Query actualizarRevisionAprobado(Integer idTurnoRevision) {
        String sql = "UPDATE sd_turno_revision SET es_aprobado = 1, hora_fin = sysdate() WHERE id_turno_revision = :idTurnoRevision";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idTurnoRevision", idTurnoRevision);
        return query;
    }

    @Override
    public Integer registrarIncidencias(TurnoRevisionIncidenciaInDto inDto){
        // Registrar incidencias
        System.out.println(inDto.toString());
        for (Integer id : inDto.getIdIncidencias()) {
            TurnoIncidencia turnoIncidencia = new TurnoIncidencia();
            turnoIncidencia.setIncidencia(new Incidencia());
            turnoIncidencia.getIncidencia().setIdIncidencia(id);
            turnoIncidencia.setTurnoRevision(new TurnoRevision());
            turnoIncidencia.getTurnoRevision().setIdTurnoRevision(inDto.getIdTurnoRevision());
            turnoIncidencia.setAuditoria(new Auditoria());
            turnoIncidencia.getAuditoria().setActivo(1);
            turnoIncidencia.getAuditoria().setFechaRegistro(new Date());
            turnoIncidencia.getAuditoria().setUsuarioRegistro("usuario");
            System.out.println(turnoIncidencia);
            crudService.create(turnoIncidencia);
            if(turnoIncidencia.getIdTurnoIncidencia() == null || turnoIncidencia.getIdTurnoIncidencia() == 0){
                return 0;
            }
        }
        // Registrar no aprobado
        Query queryAprobado = actualizarRevisionIncidencia(inDto.getIdTurnoRevision());
        int filasActualizadas = queryAprobado.executeUpdate();
        return filasActualizadas > 0 ? 1 : 0;
    }

    private Query actualizarRevisionIncidencia(Integer idTurnoRevision){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_turno_revision ");
        sql.append("SET es_aprobado = 0, hora_fin = sysdate() ");
        sql.append("WHERE id_turno_revision = :idTurnoRevision ");
        parameters.put("idTurnoRevision", idTurnoRevision);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public TurnoRevisionConductorOutDto obtenerDatosRevisionDelConductor(Integer idDespacho) throws ParseException{
        TurnoRevisionConductorOutDto outDto = new TurnoRevisionConductorOutDto();
        Query query = consultarDatosRevisionDelConductor(idDespacho);
        List<Object[]> result = query.getResultList();
        Object[] item = result.get(0);
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outDto.setIdTurnoRevision(QueryUtils.getAsInteger(item[0]));
        outDto.setCodigoPuntoControl(QueryUtils.getAsString(item[1]));
        outDto.setEsAprobado(QueryUtils.getAsInteger(item[2]));
        try{
            String horaInicioString = QueryUtils.getAsString((Date)item[3]);
            if(horaInicioString == null){
                outDto.setHoraInicio(null);
            } else {
                outDto.setHoraInicio(sdfTime.parse(horaInicioString));
            }
            String horaFinString = QueryUtils.getAsString((Date)item[4]);
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

    private Query consultarDatosRevisionDelConductor(Integer idDespacho){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT tr.id_turno_revision, "); // 0
        sql.append("pc.codigo, "); // 1
        sql.append("tr.es_aprobado, "); // 2
        sql.append("tr.hora_inicio, "); // 3
        sql.append("tr.hora_fin "); // 4
        sql.append("FROM sd_turno_revision tr ");
        sql.append("INNER JOIN sd_punto_control pc ON tr.id_punto_control = pc.id_punto_control ");
        sql.append("WHERE tr.id_despacho = :idDespacho ");
        parameters.put("idDespacho", idDespacho);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

    @Override
    public Integer salirPuntoControl(Integer idTurnoRevision){
        Query query = actualizarRevisionSalida(idTurnoRevision);
        int filasActualizadas = query.executeUpdate();
        if(filasActualizadas > 0){
            return 1;
        } else 
            return 0;
    }

    private Query actualizarRevisionSalida(Integer idTurnoRevision){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("UPDATE sd_turno_revision ");
        sql.append("SET id_revisor = null, id_punto_control = null, hora_inicio = null ");
        sql.append("WHERE id_turno_revision = :idTurnoRevision ");
        parameters.put("idTurnoRevision", idTurnoRevision);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);

        return query;
    }

    @Override
    public List<String> obtenerIncidenciasPorRevision(Integer idTurnoRevision){
        List<String> outDto = new ArrayList<>();
        Query query = consultarIncidenciasPorRevision(idTurnoRevision);
        List<String> resultList = query.getResultList();
        for (String item : resultList) {
            outDto.add(item); // Directamente añadir la cadena
        }
        return outDto;
    }

    private Query consultarIncidenciasPorRevision(Integer idTurnoRevision){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameters = new HashMap<>();

        sql.append("SELECT ic.nombre ");
        sql.append("FROM sd_incidencia ic ");
        sql.append("INNER JOIN sd_turno_incidencia ti ON ti.id_incidencia = ic.id_incidencia ");
        sql.append("WHERE ti.id_turno_revision = :idTurnoRevision ");
        parameters.put("idTurnoRevision", idTurnoRevision);

        Query query = crudService.createNativeQuery(sql.toString(), parameters);
        return query;
    }

}
