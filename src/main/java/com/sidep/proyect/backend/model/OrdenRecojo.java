package com.sidep.proyect.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sd_orden_recojo")
public class OrdenRecojo {
    @Id
    @Column(name = "id_orden_recojo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdenRecojo;

    @JoinColumn(name = "id_conductor")
    @ManyToOne
    @NotNull
    private Conductor conductor;

    @JoinColumn(name = "id_sede_cliente")
    @ManyToOne
    @NotNull
    private SedeCliente sedeCliente;

    @JoinColumn(name = "id_producto_venta")
    @ManyToOne
    @NotNull
    private ProductoVenta productoVenta;

    @JoinColumn(name = "id_estado_orden")
    @ManyToOne
    @NotNull
    private EstadoOrden estadoOrden;

    @JoinColumn(name = "id_tracto")
    @ManyToOne
    @NotNull
    private Vehiculo tracto;

    @JoinColumn(name = "id_carreta")
    @ManyToOne
    @NotNull
    private Vehiculo carreta;
    
    @Column(name = "cantidad")
    @NotNull
    private Integer cantidad;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public OrdenRecojo() {
    }

    public Integer getIdOrdenRecojo() {
        return idOrdenRecojo;
    }

    public void setIdOrdenRecojo(Integer idOrdenRecojo) {
        this.idOrdenRecojo = idOrdenRecojo;
    }

    public SedeCliente getSedeCliente() {
        return sedeCliente;
    }

    public void setSedeCliente(SedeCliente sedeCliente) {
        this.sedeCliente = sedeCliente;
    }

    public ProductoVenta getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVenta productoVenta) {
        this.productoVenta = productoVenta;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public Vehiculo getTracto() {
        return tracto;
    }

    public void setTracto(Vehiculo tracto) {
        this.tracto = tracto;
    }

    public Vehiculo getCarreta() {
        return carreta;
    }

    public void setCarreta(Vehiculo carreta) {
        this.carreta = carreta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    @Override
    public String toString() {
        return "OrdenRecojo [idOrdenRecojo=" + idOrdenRecojo + ", sedeCliente=" + sedeCliente + ", productoVenta="
                + productoVenta + ", estadoOrden=" + estadoOrden + ", tracto=" + tracto + ", carreta=" + carreta
                + ", cantidad=" + cantidad + ", auditoria=" + auditoria + "]";
    }

    

    
    
}
