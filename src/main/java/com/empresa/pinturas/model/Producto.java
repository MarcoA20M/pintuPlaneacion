package com.empresa.pinturas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    private String id;

    private String descripcion;

    @Column(name = "poder_cubriente")
    private Integer poderCubriente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_pintura_id")
    private TipoPintura tipoPintura;

    // 🔹 ESTA RELACIÓN FALTABA
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "familia_id")
    private Familia familia;

    public Producto() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPoderCubriente() { return poderCubriente; }
    public void setPoderCubriente(Integer poderCubriente) { this.poderCubriente = poderCubriente; }

    public TipoPintura getTipoPintura() { return tipoPintura; }
    public void setTipoPintura(TipoPintura tipoPintura) { this.tipoPintura = tipoPintura; }

    public Familia getFamilia() { return familia; }
    public void setFamilia(Familia familia) { this.familia = familia; }
}
