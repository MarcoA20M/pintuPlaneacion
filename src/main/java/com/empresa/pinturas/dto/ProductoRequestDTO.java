package com.empresa.pinturas.dto;

import java.util.List;

public class ProductoRequestDTO {
    private String codigo;
    private String descripcion;
    private Integer poderCubriente;
    private Long tipoPinturaId;
    private Long familiaId;
    private String color;
    private List<EnvasadoRequestDTO> envasados;
    private List<ProcesoDTO> procesos;  // ← NUEVO

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPoderCubriente() { return poderCubriente; }
    public void setPoderCubriente(Integer poderCubriente) { this.poderCubriente = poderCubriente; }

    public Long getTipoPinturaId() { return tipoPinturaId; }
    public void setTipoPinturaId(Long tipoPinturaId) { this.tipoPinturaId = tipoPinturaId; }

    public Long getFamiliaId() { return familiaId; }
    public void setFamiliaId(Long familiaId) { this.familiaId = familiaId; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<EnvasadoRequestDTO> getEnvasados() { return envasados; }
    public void setEnvasados(List<EnvasadoRequestDTO> envasados) { this.envasados = envasados; }

    public List<ProcesoDTO> getProcesos() { return procesos; }
    public void setProcesos(List<ProcesoDTO> procesos) { this.procesos = procesos; }
}