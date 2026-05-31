package com.empresa.pinturas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "familia_id")
    private Familia familia;

    private String color;

    // 🔴 NUEVO: Relación con Procesos
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Proceso> procesos = new ArrayList<>();

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

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    // 🔴 NUEVO: Getters y Setters para procesos
    public List<Proceso> getProcesos() { return procesos; }
    public void setProcesos(List<Proceso> procesos) { this.procesos = procesos; }
}