package com.empresa.pinturas.dto;

public class EnvasadoRequestDTO {
    private Integer envasadoId;
    private String articulo;
    private String descripcion;

    public EnvasadoRequestDTO() {}

    public Integer getEnvasadoId() { return envasadoId; }
    public void setEnvasadoId(Integer envasadoId) { this.envasadoId = envasadoId; }

    public String getArticulo() { return articulo; }
    public void setArticulo(String articulo) { this.articulo = articulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}