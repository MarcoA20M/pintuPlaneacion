package com.empresa.pinturas.dto;

public class EnvasadoDTO {

    private Integer id;
    private String articulo;

    // Constructor vacío (necesario para Jackson)
    public EnvasadoDTO() {
    }

    public EnvasadoDTO(Integer id, String articulo) {
        this.id = id;
        this.articulo = articulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}