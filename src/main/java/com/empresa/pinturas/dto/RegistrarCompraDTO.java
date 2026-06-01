package com.empresa.pinturas.dto;

public class RegistrarCompraDTO {
    private Long materiaPrimaId;
    private Double cantidad;
    private String documentoReferencia;
    private String observaciones;
    private String usuario;

    // Getters y Setters
    public Long getMateriaPrimaId() { return materiaPrimaId; }
    public void setMateriaPrimaId(Long materiaPrimaId) { this.materiaPrimaId = materiaPrimaId; }

    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }

    public String getDocumentoReferencia() { return documentoReferencia; }
    public void setDocumentoReferencia(String documentoReferencia) { this.documentoReferencia = documentoReferencia; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
}