package com.empresa.pinturas.controller;

import com.empresa.pinturas.dto.MateriaPrimaDTO;
import com.empresa.pinturas.dto.RegistrarCompraDTO;
import com.empresa.pinturas.model.MovimientoInventario;
import com.empresa.pinturas.service.MateriaPrimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materias-primas")
@CrossOrigin(origins = "*")
public class MateriaPrimaController {

    private final MateriaPrimaService service;

    public MateriaPrimaController(MateriaPrimaService service) {
        this.service = service;
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<MateriaPrimaDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Listar por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MateriaPrimaDTO>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    // Listar críticas
    @GetMapping("/criticas")
    public ResponseEntity<List<MateriaPrimaDTO>> listarCriticas() {
        return ResponseEntity.ok(service.listarCriticas());
    }

    // Listar en alerta
    @GetMapping("/alerta")
    public ResponseEntity<List<MateriaPrimaDTO>> listarEnAlerta() {
        return ResponseEntity.ok(service.listarEnAlerta());
    }

    // Resumen para dashboard
    @GetMapping("/dashboard/resumen")
    public ResponseEntity<Map<String, Object>> getResumenDashboard() {
        // 🔴 CORREGIDO: Cambiar a Map<String, Object>
        Map<String, Object> resumen = service.getResumenDashboard();
        return ResponseEntity.ok(resumen);
    }

    // Obtener una materia prima
    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrimaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // Registrar compra
    @PostMapping("/compra")
    public ResponseEntity<MateriaPrimaDTO> registrarCompra(@RequestBody RegistrarCompraDTO dto) {
        return ResponseEntity.ok(service.registrarCompra(dto));
    }

    // Obtener movimientos de una materia prima
    @GetMapping("/{id}/movimientos")
    public ResponseEntity<List<MovimientoInventario>> obtenerMovimientos(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerMovimientos(id));
    }
}