package com.empresa.pinturas.dto;

public class FormulaResponseDTO {
    private Long id;
    private String productoId;
    private String productoNombre;
    private String productoCodigo;
    private Long materiaPrimaId;
    private String materiaPrimaNombre;
    private String materiaPrimaCodigo;
    private String materiaPrimaTipo;
    private Double cantidadPorLitro;  // Solo esto

    public FormulaResponseDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public String getProductoCodigo() { return productoCodigo; }
    public void setProductoCodigo(String productoCodigo) { this.productoCodigo = productoCodigo; }

    public Long getMateriaPrimaId() { return materiaPrimaId; }
    public void setMateriaPrimaId(Long materiaPrimaId) { this.materiaPrimaId = materiaPrimaId; }

    public String getMateriaPrimaNombre() { return materiaPrimaNombre; }
    public void setMateriaPrimaNombre(String materiaPrimaNombre) { this.materiaPrimaNombre = materiaPrimaNombre; }

    public String getMateriaPrimaCodigo() { return materiaPrimaCodigo; }
    public void setMateriaPrimaCodigo(String materiaPrimaCodigo) { this.materiaPrimaCodigo = materiaPrimaCodigo; }

    public String getMateriaPrimaTipo() { return materiaPrimaTipo; }
    public void setMateriaPrimaTipo(String materiaPrimaTipo) { this.materiaPrimaTipo = materiaPrimaTipo; }

    public Double getCantidadPorLitro() { return cantidadPorLitro; }
    public void setCantidadPorLitro(Double cantidadPorLitro) { this.cantidadPorLitro = cantidadPorLitro; }
}