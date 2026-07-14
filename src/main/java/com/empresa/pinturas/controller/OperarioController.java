package com.empresa.pinturas.controller;

import com.empresa.pinturas.model.Operario;
import com.empresa.pinturas.model.Vacacion;
import com.empresa.pinturas.service.OperarioService;
import com.empresa.pinturas.service.VacacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, allowCredentials = "true")
public class OperarioController {
    
    @Autowired
    private OperarioService service;
    
    @Autowired
    private VacacionService vacacionService;
    
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
    
    @GetMapping("/vinilica/base")
    public ResponseEntity<Map<Integer, String>> getBase() {
        System.out.println("🔴 BASE: Solicitud recibida");
        Map<Integer, String> resultado = service.getBase();
        System.out.println("🔴 BASE: Respuesta: " + resultado);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping("/vinilica/configuracion")
    public ResponseEntity<Map<String, Object>> getConfiguracionVinilica() {
        System.out.println("🔴 CONFIG: /vinilica/configuracion");
        return ResponseEntity.ok(service.getConfiguracionVinilica());
    }
    
    @PutMapping("/vinilica/reordenar")
    public ResponseEntity<List<Operario>> reordenar(@RequestBody List<Integer> ids) {
        System.out.println("🔴 REORDENAR: ids=" + ids);
        return ResponseEntity.ok(service.reordenarVinilica(ids));
    }
    
    // ========== 📋 VACACIONES ==========
    
    // Obtener todas las vacaciones
    @GetMapping("/vacaciones")
    public ResponseEntity<List<Vacacion>> getVacaciones() {
        System.out.println("📋 VACACIONES: Obteniendo todas las vacaciones");
        return ResponseEntity.ok(vacacionService.findAll());
    }
    
    // Obtener vacaciones activas
    @GetMapping("/vacaciones/activas")
    public ResponseEntity<List<Vacacion>> getVacacionesActivas() {
        System.out.println("📋 VACACIONES: Obteniendo vacaciones activas");
        return ResponseEntity.ok(vacacionService.findActivas());
    }
    
    // Obtener vacaciones en curso
    @GetMapping("/vacaciones/curso")
    public ResponseEntity<List<Vacacion>> getVacacionesEnCurso() {
        System.out.println("📋 VACACIONES: Obteniendo vacaciones en curso");
        return ResponseEntity.ok(vacacionService.findEnCurso());
    }
    
    // Obtener vacaciones futuras
    @GetMapping("/vacaciones/futuras")
    public ResponseEntity<List<Vacacion>> getVacacionesFuturas() {
        System.out.println("📋 VACACIONES: Obteniendo vacaciones futuras");
        return ResponseEntity.ok(vacacionService.findFuturas());
    }
    
    // Obtener vacaciones de un operario
    @GetMapping("/vacaciones/operario/{operarioId}")
    public ResponseEntity<List<Vacacion>> getVacacionesByOperario(@PathVariable Integer operarioId) {
        System.out.println("📋 VACACIONES: Obteniendo vacaciones del operario " + operarioId);
        return ResponseEntity.ok(vacacionService.findByOperario(operarioId));
    }
    
    // Verificar si un operario está de vacaciones
    @GetMapping("/vacaciones/verificar/{operarioId}")
    public ResponseEntity<Map<String, Object>> estaEnVacaciones(@PathVariable Integer operarioId) {
        System.out.println("📋 VACACIONES: Verificando operario " + operarioId);
        
        boolean enVacaciones = vacacionService.estaEnVacaciones(operarioId);
        Map<String, Object> response = new HashMap<>();
        response.put("operarioId", operarioId);
        response.put("enVacaciones", enVacaciones);
        
        if (enVacaciones) {
            Vacacion vacacionActiva = vacacionService.getVacacionActiva(operarioId);
            response.put("vacacion", vacacionActiva);
        }
        
        return ResponseEntity.ok(response);
    }
    
    // Verificar múltiples operarios
    @PostMapping("/vacaciones/verificar-multiple")
    public ResponseEntity<Map<Integer, Boolean>> verificarMultiples(@RequestBody List<Integer> operarioIds) {
        System.out.println("📋 VACACIONES: Verificando múltiples operarios");
        Map<Integer, Boolean> resultado = new HashMap<>();
        for (Integer id : operarioIds) {
            resultado.put(id, vacacionService.estaEnVacaciones(id));
        }
        return ResponseEntity.ok(resultado);
    }
    
    // Crear vacación
    @PostMapping("/vacaciones")
    public ResponseEntity<?> crearVacacion(@RequestBody Vacacion vacacion) {
        System.out.println("📋 VACACIONES: Creando vacación para operario " + vacacion.getOperarioId());
        try {
            Vacacion nueva = vacacionService.save(vacacion);
            return ResponseEntity.ok(nueva);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Actualizar vacación
    @PutMapping("/vacaciones/{id}")
    public ResponseEntity<?> actualizarVacacion(@PathVariable Integer id, @RequestBody Vacacion vacacion) {
        System.out.println("📋 VACACIONES: Actualizando vacación " + id);
        try {
            vacacion.setId(id);
            return ResponseEntity.ok(vacacionService.update(vacacion));
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Eliminar vacación
    @DeleteMapping("/vacaciones/{id}")
    public ResponseEntity<Void> eliminarVacacion(@PathVariable Integer id) {
        System.out.println("📋 VACACIONES: Eliminando vacación " + id);
        vacacionService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    // Cancelar vacación
    @PatchMapping("/vacaciones/{id}/cancelar")
    public ResponseEntity<Vacacion> cancelarVacacion(@PathVariable Integer id) {
        System.out.println("📋 VACACIONES: Cancelando vacación " + id);
        return ResponseEntity.ok(vacacionService.cancelar(id));
    }
    
    // Activar vacación
    @PatchMapping("/vacaciones/{id}/activar")
    public ResponseEntity<Vacacion> activarVacacion(@PathVariable Integer id) {
        System.out.println("📋 VACACIONES: Activando vacación " + id);
        return ResponseEntity.ok(vacacionService.activar(id));
    }
    
    // Obtener IDs de operarios en vacaciones hoy
    @GetMapping("/vacaciones/operarios-en-vacaciones")
    public ResponseEntity<List<Integer>> getOperariosEnVacaciones() {
        System.out.println("📋 VACACIONES: Obteniendo operarios en vacaciones hoy");
        return ResponseEntity.ok(vacacionService.findOperariosEnVacacionesHoy());
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