package com.empresa.pinturas.model;

import jakarta.persistence.*;

@Entity
@IdClass(ProductoEnvasadoId.class)
@Table(name = "producto_envasado")
public class ProductoEnvasado {

    @Id
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Id
    @ManyToOne
    @JoinColumn(name = "envasado_id")
    private Envasado envasado;

    private String articulo;
    private String descripcion;

    public ProductoEnvasado() {}

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public Envasado getEnvasado() {
        return envasado;
    }

    public String getArticulo() {
        return articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // ✅ Setters (agregar estos)
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setEnvasado(Envasado envasado) {
        this.envasado = envasado;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}