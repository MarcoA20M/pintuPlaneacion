package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materias_primas")
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String tipo;

    @Column(length = 20)
    private String codigo;

    @Column(name = "capacidad_maxima", nullable = false)
    private Double capacidadMaxima;

    @Column(name = "nivel_actual", nullable = false)
    private Double nivelActual;

    @Column(length = 10)
    private String unidad = "L";

    @Column(name = "umbral_critico")
    private Double umbralCritico;

    @Column(name = "umbral_alerta")
    private Double umbralAlerta;

    @Column(name = "costo_por_unidad")
    private Double costoPorUnidad;

    @Column(length = 50)
    private String ubicacion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<Formula> formulas = new ArrayList<>();

    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<MovimientoInventario> movimientos = new ArrayList<>();

    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<Consumo> consumos = new ArrayList<>();

    public MateriaPrima() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Double getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Double capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public Double getNivelActual() { return nivelActual; }
    public void setNivelActual(Double nivelActual) { this.nivelActual = nivelActual; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public Double getUmbralCritico() { return umbralCritico; }
    public void setUmbralCritico(Double umbralCritico) { this.umbralCritico = umbralCritico; }

    public Double getUmbralAlerta() { return umbralAlerta; }
    public void setUmbralAlerta(Double umbralAlerta) { this.umbralAlerta = umbralAlerta; }

    public Double getCostoPorUnidad() { return costoPorUnidad; }
    public void setCostoPorUnidad(Double costoPorUnidad) { this.costoPorUnidad = costoPorUnidad; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Formula> getFormulas() { return formulas; }
    public void setFormulas(List<Formula> formulas) { this.formulas = formulas; }

    public List<MovimientoInventario> getMovimientos() { return movimientos; }
    public void setMovimientos(List<MovimientoInventario> movimientos) { this.movimientos = movimientos; }

    public List<Consumo> getConsumos() { return consumos; }
    public void setConsumos(List<Consumo> consumos) { this.consumos = consumos; }

    // Métodos útiles
    public Double getPorcentajeLlenado() {
        if (capacidadMaxima == null || capacidadMaxima == 0) return 0.0;
        return (nivelActual / capacidadMaxima) * 100;
    }

    public boolean isCritico() {
        return umbralCritico != null && nivelActual <= umbralCritico;
    }

    public boolean isAlerta() {
        return !isCritico() && umbralAlerta != null && nivelActual <= umbralAlerta;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}