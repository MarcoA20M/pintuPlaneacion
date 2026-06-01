package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lote_id", nullable = false, length = 50)
    private String loteId;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    @Column(name = "cantidad_consumida", nullable = false)
    private Double cantidadConsumida;

    @Column(name = "litros_producidos")
    private Double litrosProducidos;

    @Column(name = "fecha_consumo")
    private LocalDateTime fechaConsumo;

    @Column(length = 100)
    private String operario;

    @Column(length = 255)
    private String observaciones;

    public Consumo() {}

    public Consumo(String loteId, Producto producto, MateriaPrima materiaPrima, 
                   Double cantidadConsumida, Double litrosProducidos, String operario) {
        this.loteId = loteId;
        this.producto = producto;
        this.materiaPrima = materiaPrima;
        this.cantidadConsumida = cantidadConsumida;
        this.litrosProducidos = litrosProducidos;
        this.operario = operario;
        this.fechaConsumo = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getLoteId() { return loteId; }
    public Producto getProducto() { return producto; }
    public MateriaPrima getMateriaPrima() { return materiaPrima; }
    public Double getCantidadConsumida() { return cantidadConsumida; }
    public Double getLitrosProducidos() { return litrosProducidos; }
    public LocalDateTime getFechaConsumo() { return fechaConsumo; }
    public String getOperario() { return operario; }
    public String getObservaciones() { return observaciones; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setLoteId(String loteId) { this.loteId = loteId; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setMateriaPrima(MateriaPrima materiaPrima) { this.materiaPrima = materiaPrima; }
    public void setCantidadConsumida(Double cantidadConsumida) { this.cantidadConsumida = cantidadConsumida; }
    public void setLitrosProducidos(Double litrosProducidos) { this.litrosProducidos = litrosProducidos; }
    public void setFechaConsumo(LocalDateTime fechaConsumo) { this.fechaConsumo = fechaConsumo; }
    public void setOperario(String operario) { this.operario = operario; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}