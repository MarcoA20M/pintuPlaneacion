// MateriaPrimaDTO.java
package com.empresa.pinturas.dto;

public class MateriaPrimaDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String codigo;
    private Double capacidadMaxima;
    private Double nivelActual;
    private String unidad;
    private Double umbralCritico;
    private Double umbralAlerta;
    private Double porcentajeLlenado;
    private Boolean critico;
    private Boolean alerta;
    private String ubicacion;

    // Constructor vacío
    public MateriaPrimaDTO() {}

    // Constructor completo
    public MateriaPrimaDTO(Long id, String nombre, String tipo, String codigo, 
                           Double capacidadMaxima, Double nivelActual, String unidad,
                           Double umbralCritico, Double umbralAlerta, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.codigo = codigo;
        this.capacidadMaxima = capacidadMaxima;
        this.nivelActual = nivelActual;
        this.unidad = unidad;
        this.umbralCritico = umbralCritico;
        this.umbralAlerta = umbralAlerta;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Double getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Double capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public Double getNivelActual() { return nivelActual; }
    public void setNivelActual(Double nivelActual) { this.nivelActual = nivelActual; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public Double getUmbralCritico() { return umbralCritico; }
    public void setUmbralCritico(Double umbralCritico) { this.umbralCritico = umbralCritico; }

    public Double getUmbralAlerta() { return umbralAlerta; }
    public void setUmbralAlerta(Double umbralAlerta) { this.umbralAlerta = umbralAlerta; }

    public Double getPorcentajeLlenado() { return porcentajeLlenado; }
    public void setPorcentajeLlenado(Double porcentajeLlenado) { this.porcentajeLlenado = porcentajeLlenado; }

    public Boolean getCritico() { return critico; }
    public void setCritico(Boolean critico) { this.critico = critico; }

    public Boolean getAlerta() { return alerta; }
    public void setAlerta(Boolean alerta) { this.alerta = alerta; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}

