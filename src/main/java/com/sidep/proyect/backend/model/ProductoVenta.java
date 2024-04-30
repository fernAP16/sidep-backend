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
@Table(name = "sd_producto_venta")
public class ProductoVenta {
    @Id
    @Column(name = "id_producto_venta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProductoVenta;

    @JoinColumn(name = "id_producto")
    @ManyToOne
    @NotNull
    private Producto producto;

    @JoinColumn(name = "id_marca")
    @ManyToOne
    @NotNull
    private Marca marca;

    @JoinColumn(name = "id_unidad")
    @ManyToOne
    @NotNull
    private Unidad unidad;

    @Column(name = "stock_actual")
    @NotNull
    private Integer stockActual;

    @Embedded
    @Valid
    private Auditoria auditoria;

    public ProductoVenta() {
    }

    public Integer getIdProductoVenta() {
        return idProductoVenta;
    }

    public void setIdProductoVenta(Integer idProductoVenta) {
        this.idProductoVenta = idProductoVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return "ProductoVenta [idProductoVenta=" + idProductoVenta + ", producto=" + producto + ", marca=" + marca
                + ", unidad=" + unidad + ", stockActual=" + stockActual + ", auditoria=" + auditoria + "]";
    }

    
    
}
