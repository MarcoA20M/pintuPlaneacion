package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "formulas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"producto_id", "materia_prima_id"})
})
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    @Column(name = "cantidad_por_litro", nullable = false)
    private Double cantidadPorLitro;  // Cuántos litros de materia prima por cada litro de producto

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Formula() {}

    public Formula(Producto producto, MateriaPrima materiaPrima, Double cantidadPorLitro) {
        this.producto = producto;
        this.materiaPrima = materiaPrima;
        this.cantidadPorLitro = cantidadPorLitro;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public MateriaPrima getMateriaPrima() { return materiaPrima; }
    public Double getCantidadPorLitro() { return cantidadPorLitro; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setMateriaPrima(MateriaPrima materiaPrima) { this.materiaPrima = materiaPrima; }
    public void setCantidadPorLitro(Double cantidadPorLitro) { this.cantidadPorLitro = cantidadPorLitro; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}