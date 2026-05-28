package com.empresa.pinturas.dto;

public class EnvasadoRequestDTO {
    private String articulo;

    public EnvasadoRequestDTO() {}

    public EnvasadoRequestDTO(String articulo) {
        this.articulo = articulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}