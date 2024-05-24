package com.sidep.proyect.backend.dto.out;

public class DespachoDatosOutDto {

    private Integer idOrdenRecojo;
    private String razonSocial;
    private String placaTracto;
    private String placaCarreta;
    private String producto;
    private String cantidad;

    public DespachoDatosOutDto() {
    }
    
    public Integer getIdOrdenRecojo() {
        return idOrdenRecojo;
    }
    public void setIdOrdenRecojo(Integer idOrdenRecojo) {
        this.idOrdenRecojo = idOrdenRecojo;
    }
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getPlacaTracto() {
        return placaTracto;
    }
    public void setPlacaTracto(String placaTracto) {
        this.placaTracto = placaTracto;
    }
    public String getPlacaCarreta() {
        return placaCarreta;
    }
    public void setPlacaCarreta(String placaCarreta) {
        this.placaCarreta = placaCarreta;
    }
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    
}
