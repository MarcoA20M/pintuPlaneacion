// src/main/java/com/empresa/pinturas/dto/PlanificadorResponse.java
package com.empresa.pinturas.dto;

import java.time.LocalDateTime;

public class PlanificadorResponse {
    private String status;
    private String mensaje;
    private Object datos;  // Puede ser String o Map
    private LocalDateTime fechaActualizacion;
    
    public PlanificadorResponse() {}
    
    public PlanificadorResponse(String status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public Object getDatos() { return datos; }
    public void setDatos(Object datos) { this.datos = datos; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { 
        this.fechaActualizacion = fechaActualizacion; 
    }
}