// src/main/java/com/empresa/pinturas/controller/PlanificadorController.java
package com.empresa.pinturas.controller;

import com.empresa.pinturas.dto.PlanificadorResponse;
import com.empresa.pinturas.service.PlanificadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/api/planificador")
@CrossOrigin(origins = "*")
public class PlanificadorController {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanificadorController.class);
    
    @Autowired
    private PlanificadorService planificadorService;
    
    /**
     * Guardar planificador
     * POST /api/planificador/guardar
     * Body: { "datos": "{\"color\":\"001M\",...}" }  ← JSON como String
     */
    @PostMapping("/guardar")
    public ResponseEntity<PlanificadorResponse> guardarPlanificador(@RequestBody Map<String, Object> request) {
        logger.info("Recibida petición para guardar planificador");
        
        try {
            // ⭐ Obtener los datos como String (ya viene en JSON)
            Object datosObj = request.get("datos");
            String datosJson = null;
            
            if (datosObj instanceof String) {
                datosJson = (String) datosObj;
            } else if (datosObj != null) {
                // Si no es String, convertirlo
                datosJson = datosObj.toString();
            }
            
            if (datosJson == null || datosJson.isEmpty()) {
                PlanificadorResponse error = new PlanificadorResponse("error", "No se recibieron datos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            PlanificadorResponse response = planificadorService.guardarPlanificador(datosJson);
            
            if ("error".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error en guardarPlanificador", e);
            PlanificadorResponse error = new PlanificadorResponse("error", "Error interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Cargar planificador
     * GET /api/planificador/cargar
     */
    @GetMapping("/cargar")
    public ResponseEntity<PlanificadorResponse> cargarPlanificador() {
        logger.info("Recibida petición para cargar planificador");
        
        try {
            PlanificadorResponse response = planificadorService.cargarPlanificador();
            
            if ("empty".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            if ("error".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error en cargarPlanificador", e);
            PlanificadorResponse error = new PlanificadorResponse("error", "Error interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Eliminar planificador
     * DELETE /api/planificador/eliminar
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPlanificador() {
        logger.info("Recibida petición para eliminar planificador");
        
        try {
            boolean eliminado = planificadorService.eliminarPlanificador();
            
            if (eliminado) {
                return ResponseEntity.ok().body("Planificador eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró planificador");
            }
            
        } catch (Exception e) {
            logger.error("Error en eliminarPlanificador", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar: " + e.getMessage());
        }
    }
}