package com.empresa.pinturas.dto;

public class ProcesoDTO {

    private Integer paso;
    private String descripcion;

    public ProcesoDTO(Integer paso, String descripcion) {
        this.paso = paso;
        this.descripcion = descripcion;
    }

    public Integer getPaso() {
        return paso;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
