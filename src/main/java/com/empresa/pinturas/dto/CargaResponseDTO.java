package com.empresa.pinturas.dto;

public class CargaResponseDTO {

    private Long id;
    private String producto; // Código del producto
    private Integer envasado; // ID del envasado
    private Double litros;
    private String folio; // Antes llamado lote
    private Integer cantidad; // Cantidad de botes
    private String folioHija; // Folio individual

    // ✅ Constructor actualizado para incluir los nuevos campos
    public CargaResponseDTO(Long id, String producto, Integer envasado, Double litros, 
                            String folio, Integer cantidad, String folioHija) {
        this.id = id;
        this.producto = producto;
        this.envasado = envasado;
        this.litros = litros;
        this.folio = folio;
        this.cantidad = cantidad;
        this.folioHija = folioHija;
    }

    // --- GETTERS ---
    public Long getId() { return id; }
    public String getProducto() { return producto; }
    public Integer getEnvasado() { return envasado; }
    public Double getLitros() { return litros; }
    public String getFolio() { return folio; }
    public Integer getCantidad() { return cantidad; }
    public String getFolioHija() { return folioHija; }
}