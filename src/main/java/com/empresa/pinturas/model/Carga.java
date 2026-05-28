package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cargas")
public class Carga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "envasado_id")
    private Envasado envasado;

    private Integer cantidad; 
    private Double litros;
    private String tipo; 
    private String folio; // Folio Madre
    private String folioHija;
    private String operario;
    private String maquina;
    private LocalDateTime fecha = LocalDateTime.now();

    public Carga() {}
    // Getters y Setters de todos los campos...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Envasado getEnvasado() { return envasado; }
    public void setEnvasado(Envasado envasado) { this.envasado = envasado; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getLitros() { return litros; }
    public void setLitros(Double litros) { this.litros = litros; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    public String getFolioHija() { return folioHija; }
    public void setFolioHija(String folioHija) { this.folioHija = folioHija; }
    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }
    public String getMaquina() { return maquina; }
    public void setMaquina(String maquina) { this.maquina = maquina; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}