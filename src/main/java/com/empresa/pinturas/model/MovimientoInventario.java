package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Double cantidad;

    @Column(name = "cantidad_antes")
    private Double cantidadAntes;

    @Column(name = "cantidad_despues")
    private Double cantidadDespues;

    @Column(name = "documento_referencia", length = 50)
    private String documentoReferencia;

    @Column(length = 255)
    private String observaciones;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;

    @Column(length = 100)
    private String usuario;

    public enum TipoMovimiento {
        COMPRA, CONSUMO, AJUSTE, DEVOLUCION
    }

    public MovimientoInventario() {}

    public MovimientoInventario(MateriaPrima materiaPrima, TipoMovimiento tipo, 
                                Double cantidad, Double cantidadAntes, 
                                Double cantidadDespues, String documentoReferencia, 
                                String usuario) {
        this.materiaPrima = materiaPrima;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.cantidadAntes = cantidadAntes;
        this.cantidadDespues = cantidadDespues;
        this.documentoReferencia = documentoReferencia;
        this.usuario = usuario;
        this.fechaMovimiento = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public MateriaPrima getMateriaPrima() { return materiaPrima; }
    public TipoMovimiento getTipo() { return tipo; }
    public Double getCantidad() { return cantidad; }
    public Double getCantidadAntes() { return cantidadAntes; }
    public Double getCantidadDespues() { return cantidadDespues; }
    public String getDocumentoReferencia() { return documentoReferencia; }
    public String getObservaciones() { return observaciones; }
    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public String getUsuario() { return usuario; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setMateriaPrima(MateriaPrima materiaPrima) { this.materiaPrima = materiaPrima; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }
    public void setCantidadAntes(Double cantidadAntes) { this.cantidadAntes = cantidadAntes; }
    public void setCantidadDespues(Double cantidadDespues) { this.cantidadDespues = cantidadDespues; }
    public void setDocumentoReferencia(String documentoReferencia) { this.documentoReferencia = documentoReferencia; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
}