// src/main/java/com/empresa/pinturas/model/PlanificadorData.java
package com.empresa.pinturas.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "planificador_data")
public class PlanificadorData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "datos_json", columnDefinition = "LONGTEXT")
    private String datosJson;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructor por defecto
    public PlanificadorData() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDatosJson() { return datosJson; }
    public void setDatosJson(String datosJson) { this.datosJson = datosJson; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { 
        this.fechaActualizacion = fechaActualizacion; 
    }
}