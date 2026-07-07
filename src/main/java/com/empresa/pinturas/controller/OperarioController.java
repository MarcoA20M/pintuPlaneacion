package com.empresa.pinturas.controller;

import com.empresa.pinturas.model.Operario;
import com.empresa.pinturas.service.OperarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operarios")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OperarioController {
    
    @Autowired
    private OperarioService service;
    
    // ========== OBTENER OPERARIOS ==========
    @GetMapping("/vinilica")
    public ResponseEntity<List<Operario>> getVinilica() {
        return ResponseEntity.ok(service.getVinilica());
    }
    
    @GetMapping("/esmaltes")
    public ResponseEntity<List<Operario>> getEsmaltes() {
        return ResponseEntity.ok(service.getEsmaltes());
    }
    
    @GetMapping("/especiales")
    public ResponseEntity<List<Operario>> getEspeciales() {
        return ResponseEntity.ok(service.getEspeciales());
    }
    
    // ========== 🔄 ROTACIÓN ==========
    
    // 🔴 ENDPOINT SIMPLE PARA ROTACIÓN POR SEMANAS
    @GetMapping("/vinilica/rotar")
    public ResponseEntity<Map<Integer, String>> rotar(
            @RequestParam(defaultValue = "0") int semanas) {
        System.out.println("========================================");
        System.out.println("🔴 ROTACIÓN: Solicitud recibida con semanas=" + semanas);
        System.out.println("========================================");
        
        Map<Integer, String> resultado = service.rotar(semanas);
        
        System.out.println("🔴 ROTACIÓN: Respuesta enviada: " + resultado);
        System.out.println("========================================");
        
        return ResponseEntity.ok(resultado);
    }
    
    // 🔴 ENDPOINT PARA OBTENER ORDEN BASE
    @GetMapping("/vinilica/base")
    public ResponseEntity<Map<Integer, String>> getBase() {
        System.out.println("🔴 BASE: Solicitud recibida");
        Map<Integer, String> resultado = service.getBase();
        System.out.println("🔴 BASE: Respuesta: " + resultado);
        return ResponseEntity.ok(resultado);
    }
    
    // ========== MÉTODOS LEGACY (para compatibilidad) ==========
    
    @GetMapping("/vinilica/rotacion/semanas")
    public ResponseEntity<Map<Integer, String>> getRotacionPorSemanas(
            @RequestParam(defaultValue = "0") int semanas,
            @RequestParam(defaultValue = "false") boolean sinRotacion) {
        System.out.println("🔴 LEGACY: /vinilica/rotacion/semanas con semanas=" + semanas);
        return ResponseEntity.ok(service.rotar(semanas));
    }
    
    @GetMapping("/vinilica/rotacion")
    public ResponseEntity<Map<Integer, String>> getRotacion(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false, defaultValue = "false") boolean sinRotacion) {
        System.out.println("🔴 LEGACY: /vinilica/rotacion");
        return ResponseEntity.ok(service.getBase());
    }
    
    @GetMapping("/vinilica/rotacion/base")
    public ResponseEntity<Map<Integer, String>> getRotacionBase() {
        System.out.println("🔴 LEGACY: /vinilica/rotacion/base");
        return ResponseEntity.ok(service.getBase());
    }
    
    @GetMapping("/vinilica/configuracion")
    public ResponseEntity<Map<String, Object>> getConfiguracionVinilica() {
        System.out.println("🔴 CONFIG: /vinilica/configuracion");
        return ResponseEntity.ok(service.getConfiguracionVinilica());
    }
    
    // ========== REORDENAR ==========
    @PutMapping("/vinilica/reordenar")
    public ResponseEntity<List<Operario>> reordenar(@RequestBody List<Integer> ids) {
        System.out.println("🔴 REORDENAR: ids=" + ids);
        return ResponseEntity.ok(service.reordenarVinilica(ids));
    }
    
    // ========== CRUD ==========
    @PostMapping
    public ResponseEntity<Operario> crear(@RequestBody Operario operario) {
        System.out.println("🔴 CREAR: " + operario.getNombre());
        return ResponseEntity.ok(service.crear(operario));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Operario> actualizar(@PathVariable Integer id, @RequestBody Operario operario) {
        System.out.println("🔴 ACTUALIZAR: id=" + id);
        return ResponseEntity.ok(service.actualizar(id, operario));
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Operario> toggleActivo(@PathVariable Integer id) {
        System.out.println("🔴 TOGGLE: id=" + id);
        return ResponseEntity.ok(service.toggleActivo(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        System.out.println("🔴 ELIMINAR: id=" + id);
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}