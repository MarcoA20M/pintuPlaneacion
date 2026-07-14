// src/main/java/com/empresa/pinturas/model/Vacacion.java
package com.empresa.pinturas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacaciones")
public class Vacacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "operario_id", nullable = false)
    private Integer operarioId;
    
    @Column(name = "fecha_inicio", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relación con Operario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operario_id", insertable = false, updatable = false)
    private Operario operario;
    
    // ========== CONSTRUCTORES ==========
    public Vacacion() {}
    
    public Vacacion(Integer operarioId, LocalDate fechaInicio, LocalDate fechaFin, String observaciones) {
        this.operarioId = operarioId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observaciones = observaciones;
        this.activo = true;
    }
    
    // ========== GETTERS Y SETTERS ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getOperarioId() { return operarioId; }
    public void setOperarioId(Integer operarioId) { this.operarioId = operarioId; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Operario getOperario() { return operario; }
    public void setOperario(Operario operario) { this.operario = operario; }
    
    // ========== MÉTODOS DE AUDITORÍA ==========
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (activo == null) activo = true;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // ========== MÉTODOS DE UTILIDAD ==========
    public boolean estaEnCurso() {
        LocalDate hoy = LocalDate.now();
        return activo && !fechaInicio.isAfter(hoy) && !fechaFin.isBefore(hoy);
    }
    
    public boolean esFutura() {
        return activo && fechaInicio.isAfter(LocalDate.now());
    }
}