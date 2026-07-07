// com.empresa.pinturas.dto.ConsumoDTO.java
package com.empresa.pinturas.dto;

import com.empresa.pinturas.model.Consumo;
import java.time.LocalDateTime;

public class ConsumoDTO {
    private Long id;
    private String loteId;
    private String codigoProducto;
    private String descripcionProducto;  // 🔴 CAMBIADO: nombre → descripcion
    private String codigoBase;
    private String nombreBase;
    private Double cantidadConsumida;
    private Double litrosProducidos;
    private LocalDateTime fechaConsumo;
    private String operario;
    private String observaciones;

    // Constructor desde Consumo
    public ConsumoDTO(Consumo consumo) {
        this.id = consumo.getId();
        this.loteId = consumo.getLoteId();
        this.codigoProducto = consumo.getProducto() != null ? consumo.getProducto().getId() : null;
        this.descripcionProducto = consumo.getProducto() != null ? consumo.getProducto().getDescripcion() : null;  // 🔴 CAMBIADO
        this.codigoBase = consumo.getMateriaPrima() != null ? consumo.getMateriaPrima().getCodigo() : null;
        this.nombreBase = consumo.getMateriaPrima() != null ? consumo.getMateriaPrima().getNombre() : null;
        this.cantidadConsumida = consumo.getCantidadConsumida();
        this.litrosProducidos = consumo.getLitrosProducidos();
        this.fechaConsumo = consumo.getFechaConsumo();
        this.operario = consumo.getOperario();
        this.observaciones = consumo.getObservaciones();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLoteId() { return loteId; }
    public void setLoteId(String loteId) { this.loteId = loteId; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getDescripcionProducto() { return descripcionProducto; }  // 🔴 CAMBIADO
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }  // 🔴 CAMBIADO

    public String getCodigoBase() { return codigoBase; }
    public void setCodigoBase(String codigoBase) { this.codigoBase = codigoBase; }

    public String getNombreBase() { return nombreBase; }
    public void setNombreBase(String nombreBase) { this.nombreBase = nombreBase; }

    public Double getCantidadConsumida() { return cantidadConsumida; }
    public void setCantidadConsumida(Double cantidadConsumida) { this.cantidadConsumida = cantidadConsumida; }

    public Double getLitrosProducidos() { return litrosProducidos; }
    public void setLitrosProducidos(Double litrosProducidos) { this.litrosProducidos = litrosProducidos; }

    public LocalDateTime getFechaConsumo() { return fechaConsumo; }
    public void setFechaConsumo(LocalDateTime fechaConsumo) { this.fechaConsumo = fechaConsumo; }

    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}