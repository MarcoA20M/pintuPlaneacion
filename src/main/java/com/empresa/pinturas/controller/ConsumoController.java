// com.empresa.pinturas.controller.ConsumoController.java
package com.empresa.pinturas.controller;

import com.empresa.pinturas.model.Consumo;
import com.empresa.pinturas.model.MateriaPrima;
import com.empresa.pinturas.repository.MateriaPrimaRepository;
import com.empresa.pinturas.service.ConsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consumos")
@CrossOrigin(origins = "*")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    // 🔴 GUARDAR CONSUMOS (MÚLTIPLES)
    @PostMapping
    public ResponseEntity<?> guardarConsumos(@RequestBody List<Map<String, Object>> consumosData) {
        try {
            List<Consumo> consumos = new ArrayList<>();
            
            for (Map<String, Object> data : consumosData) {
                String loteId = (String) data.get("loteId");
                String materiaPrimaCodigo = (String) data.get("materiaPrimaCodigo");
                Double cantidadConsumida = ((Number) data.get("cantidadConsumida")).doubleValue();
                Double litrosProducidos = data.get("litrosProducidos") != null ? 
                    ((Number) data.get("litrosProducidos")).doubleValue() : 0.0;
                String operario = (String) data.getOrDefault("operario", "Admin");
                String observaciones = (String) data.getOrDefault("observaciones", "");
                
                // Buscar materia prima por código
                MateriaPrima materiaPrima = materiaPrimaRepository.findByCodigo(materiaPrimaCodigo)
                    .orElseThrow(() -> new RuntimeException("Materia prima no encontrada: " + materiaPrimaCodigo));
                
                Consumo consumo = new Consumo();
                consumo.setLoteId(loteId);
                consumo.setMateriaPrima(materiaPrima);
                consumo.setCantidadConsumida(cantidadConsumida);
                consumo.setLitrosProducidos(litrosProducidos);
                consumo.setOperario(operario);
                consumo.setObservaciones(observaciones);
                consumo.setFechaConsumo(LocalDateTime.now());
                
                consumos.add(consumo);
            }
            
            List<Consumo> registros = consumoService.guardarConsumos(consumos);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Consumos guardados correctamente");
            respuesta.put("cantidad", registros.size());
            
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 🔴 OBTENER CONSUMOS POR CÓDIGO DE MATERIA PRIMA (BASE)
    @GetMapping("/base/{materiaCodigo}")
    public ResponseEntity<?> getConsumosPorBase(@PathVariable String materiaCodigo) {
        try {
            List<Consumo> consumos = consumoService.listarPorMateriaPrima(materiaCodigo);
            
            List<Map<String, Object>> resultado = new ArrayList<>();
            for (Consumo c : consumos) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", c.getId());
                item.put("loteId", c.getLoteId());
                item.put("codigoProducto", c.getProducto() != null ? c.getProducto().getId() : null);
                item.put("descripcionProducto", c.getProducto() != null ? c.getProducto().getDescripcion() : null);
                item.put("cantidadConsumida", c.getCantidadConsumida());
                item.put("litrosProducidos", c.getLitrosProducidos());
                item.put("fechaConsumo", c.getFechaConsumo());
                item.put("operario", c.getOperario());
                item.put("observaciones", c.getObservaciones());
                resultado.add(item);
            }
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}