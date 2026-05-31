package com.empresa.pinturas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "procesos")
public class Proceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer paso;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Proceso() {}

    // Getters
    public Long getId() { return id; }
    public Integer getPaso() { return paso; }
    public String getDescripcion() { return descripcion; }
    public Producto getProducto() { return producto; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPaso(Integer paso) { this.paso = paso; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setProducto(Producto producto) { this.producto = producto; }
}