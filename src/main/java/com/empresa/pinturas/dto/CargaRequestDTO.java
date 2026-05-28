package com.empresa.pinturas.dto;

public class CargaRequestDTO {
    private String codigoProducto;
    private Integer envasadoId;
    private Double litros;
    private String tipo;
    private Integer cantidad;
    private String folio;
    private String folioHija;
    private String operario;
    private String maquina;

    // Getters y Setters...
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public Integer getEnvasadoId() { return envasadoId; }
    public void setEnvasadoId(Integer envasadoId) { this.envasadoId = envasadoId; }
    public Double getLitros() { return litros; }
    public void setLitros(Double litros) { this.litros = litros; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    public String getFolioHija() { return folioHija; }
    public void setFolioHija(String folioHija) { this.folioHija = folioHija; }
    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }
    public String getMaquina() { return maquina; }
    public void setMaquina(String maquina) { this.maquina = maquina; }
}