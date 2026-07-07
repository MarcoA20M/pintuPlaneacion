package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operarios")
public class Operario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 50)
    private String puesto;
    
    @Column(nullable = false, length = 20)
    private String area; // "vinilica", "esmalte", "especial"
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(name = "orden_vinilica")
    private Integer ordenVinilica = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // ========== CONSTRUCTORES ==========
    public Operario() {}
    
    public Operario(String nombre, String puesto, String area) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.area = area;
        this.activo = true;
        this.ordenVinilica = 0;
    }
    
    // ========== GETTERS Y SETTERS ==========
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getPuesto() {
        return puesto;
    }
    
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Integer getOrdenVinilica() {
        return ordenVinilica;
    }
    
    public void setOrdenVinilica(Integer ordenVinilica) {
        this.ordenVinilica = ordenVinilica;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // ========== MÉTODOS DE AUDITORÍA ==========
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // ========== TOSTRING ==========
    @Override
    public String toString() {
        return "Operario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puesto='" + puesto + '\'' +
                ", area='" + area + '\'' +
                ", activo=" + activo +
                '}';
    }
}