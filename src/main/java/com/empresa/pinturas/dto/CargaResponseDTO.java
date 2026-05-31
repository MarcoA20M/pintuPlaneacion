package com.empresa.pinturas.dto;

public class CargaResponseDTO {
    private Long id;
    private String producto;  // o productoId
    private Integer envasado;  // o envasadoId
    private Double litros;
    private String folio;
    private Integer cantidad;
    private String folioHija;
    private String operario;   // ← NUEVO
    private String maquina;     // ← NUEVO
    private String tipo;        // ← NUEVO (opcional)

    // Constructor con todos los campos
    public CargaResponseDTO(Long id, String producto, Integer envasado, Double litros, 
                            String folio, Integer cantidad, String folioHija, 
                            String operario, String maquina, String tipo) {
        this.id = id;
        this.producto = producto;
        this.envasado = envasado;
        this.litros = litros;
        this.folio = folio;
        this.cantidad = cantidad;
        this.folioHija = folioHija;
        this.operario = operario;
        this.maquina = maquina;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    
    public Integer getEnvasado() { return envasado; }
    public void setEnvasado(Integer envasado) { this.envasado = envasado; }
    
    public Double getLitros() { return litros; }
    public void setLitros(Double litros) { this.litros = litros; }
    
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public String getFolioHija() { return folioHija; }
    public void setFolioHija(String folioHija) { this.folioHija = folioHija; }
    
    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }
    
    public String getMaquina() { return maquina; }
    public void setMaquina(String maquina) { this.maquina = maquina; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}