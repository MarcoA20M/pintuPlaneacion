package com.empresa.pinturas.dto;

public class ProcesoDTO {
    private Long id;
    private Integer paso;
    private String descripcion;

    public ProcesoDTO() {}

    public ProcesoDTO(Long id, Integer paso, String descripcion) {
        this.id = id;
        this.paso = paso;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getPaso() { return paso; }
    public void setPaso(Integer paso) { this.paso = paso; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}