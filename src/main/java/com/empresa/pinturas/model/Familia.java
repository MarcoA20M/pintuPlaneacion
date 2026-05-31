package com.empresa.pinturas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "familias")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambiar a IDENTITY
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false)
    private String tipo; // "vinilica" o "esmalte"

    @Column(name = "imagen_url", nullable = true)
    private String imagenUrl;

    // 🔴 CONSTRUCTOR VACÍO OBLIGATORIO
    public Familia() {
    }

    // Constructor con parámetros (opcional)
    public Familia(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    // ========== GETTERS Y SETTERS ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}