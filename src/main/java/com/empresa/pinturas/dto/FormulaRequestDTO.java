package com.empresa.pinturas.dto;

public class FormulaRequestDTO {
    private String productoId;
    private Long materiaPrimaId;
    private Double cantidadPorLitro;
    private Double cantidadPorBote;

    public FormulaRequestDTO() {}

    public FormulaRequestDTO(String productoId, Long materiaPrimaId, Double cantidadPorLitro, Double cantidadPorBote) {
        this.productoId = productoId;
        this.materiaPrimaId = materiaPrimaId;
        this.cantidadPorLitro = cantidadPorLitro;
        this.cantidadPorBote = cantidadPorBote;
    }

    // Getters y Setters
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public Long getMateriaPrimaId() { return materiaPrimaId; }
    public void setMateriaPrimaId(Long materiaPrimaId) { this.materiaPrimaId = materiaPrimaId; }

    public Double getCantidadPorLitro() { return cantidadPorLitro; }
    public void setCantidadPorLitro(Double cantidadPorLitro) { this.cantidadPorLitro = cantidadPorLitro; }

    public Double getCantidadPorBote() { return cantidadPorBote; }
    public void setCantidadPorBote(Double cantidadPorBote) { this.cantidadPorBote = cantidadPorBote; }
}