package com.empresa.pinturas.controller;

import com.empresa.pinturas.dto.FormulaRequestDTO;
import com.empresa.pinturas.dto.FormulaResponseDTO;
import com.empresa.pinturas.service.FormulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/formulas")
@CrossOrigin(origins = "*")
public class FormulaController {

    private final FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }

    // Listar fórmulas por producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<FormulaResponseDTO>> listarPorProducto(@PathVariable String productoId) {
        return ResponseEntity.ok(formulaService.listarPorProducto(productoId));
    }

    // Crear nueva fórmula
    @PostMapping
    public ResponseEntity<FormulaResponseDTO> crear(@RequestBody FormulaRequestDTO dto) {
        FormulaResponseDTO nueva = formulaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // Actualizar fórmula
    @PutMapping("/{id}")
    public ResponseEntity<FormulaResponseDTO> actualizar(@PathVariable Long id, @RequestBody FormulaRequestDTO dto) {
        return ResponseEntity.ok(formulaService.actualizar(id, dto));
    }

    // Eliminar fórmula
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        formulaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}