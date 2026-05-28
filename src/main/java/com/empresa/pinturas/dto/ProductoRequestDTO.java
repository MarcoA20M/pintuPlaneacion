package com.empresa.pinturas.dto;

import java.util.List;

public class ProductoRequestDTO {
    private String codigo;
    private String descripcion;
    private Integer poderCubriente;
    private Long tipoPinturaId;
    private List<EnvasadoRequestDTO> envasados;  // ← Agrega este campo

    // Constructor vacío
    public ProductoRequestDTO() {}

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

    public List<EnvasadoRequestDTO> getEnvasados() {
        return envasados;
    }

    public void setEnvasados(List<EnvasadoRequestDTO> envasados) {
        this.envasados = envasados;
    }
}