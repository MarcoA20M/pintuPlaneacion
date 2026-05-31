package com.empresa.pinturas.dto;

import java.util.List;

public class ProductoDetalleDTO {
    private String codigo;
    private String descripcion;
    private Integer poderCubriente;
    private Long tipoPinturaId;
    private Long familiaId;
    private String color;  // ← AGREGAR color
    private List<EnvasadoDTO> envasados;
    private List<ProcesoDTO> procesos;  // ← Cambiar List<?> a List<ProcesoDTO>

    // Constructor vacío
    public ProductoDetalleDTO() {}

    // Constructor con parámetros
    public ProductoDetalleDTO(String codigo, String descripcion, Integer poderCubriente, 
                              Long tipoPinturaId, Long familiaId, String color,
                              List<EnvasadoDTO> envasados, List<ProcesoDTO> procesos) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.poderCubriente = poderCubriente;
        this.tipoPinturaId = tipoPinturaId;
        this.familiaId = familiaId;
        this.color = color;
        this.envasados = envasados;
        this.procesos = procesos;
    }

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

    public List<EnvasadoDTO> getEnvasados() { return envasados; }
    public void setEnvasados(List<EnvasadoDTO> envasados) { this.envasados = envasados; }

    public List<ProcesoDTO> getProcesos() { return procesos; }
    public void setProcesos(List<ProcesoDTO> procesos) { this.procesos = procesos; }
}