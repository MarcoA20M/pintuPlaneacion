package com.empresa.pinturas.dto;

import java.util.List;

public class ProductoDetalleDTO {
    private String codigo;
    private String descripcion;
    private Integer poderCubriente;
    private Long tipoPinturaId;
    private List<EnvasadoDTO> envasados;
    private List<?> procesos;

    // ✅ Constructor vacío (necesario para Jackson)
    public ProductoDetalleDTO() {
    }

    // Constructor con parámetros
    public ProductoDetalleDTO(String codigo, String descripcion, Integer poderCubriente, 
                              Long tipoPinturaId, List<EnvasadoDTO> envasados, List<?> procesos) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.poderCubriente = poderCubriente;
        this.tipoPinturaId = tipoPinturaId;
        this.envasados = envasados;
        this.procesos = procesos;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPoderCubriente() {
        return poderCubriente;
    }

    public void setPoderCubriente(Integer poderCubriente) {
        this.poderCubriente = poderCubriente;
    }

    public Long getTipoPinturaId() {
        return tipoPinturaId;
    }

    public void setTipoPinturaId(Long tipoPinturaId) {
        this.tipoPinturaId = tipoPinturaId;
    }

    public List<EnvasadoDTO> getEnvasados() {
        return envasados;
    }

    public void setEnvasados(List<EnvasadoDTO> envasados) {
        this.envasados = envasados;
    }

    public List<?> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<?> procesos) {
        this.procesos = procesos;
    }
}