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

    public Integer getPaso() {
        return paso;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
