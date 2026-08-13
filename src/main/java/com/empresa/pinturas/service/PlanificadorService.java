// src/main/java/com/empresa/pinturas/service/PlanificadorService.java
package com.empresa.pinturas.service;

import com.empresa.pinturas.dto.PlanificadorResponse;
import com.empresa.pinturas.model.PlanificadorData;
import com.empresa.pinturas.repository.PlanificadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlanificadorService {
    
    private static final Logger logger = LoggerFactory.getLogger(PlanificadorService.class);
    
    @Autowired
    private PlanificadorRepository repository;
    
    /**
     * Guarda el planificador - Recibe el JSON como String
     */
    @Transactional
    public PlanificadorResponse guardarPlanificador(String datosJson) {
        try {
            if (datosJson == null || datosJson.isEmpty()) {
                return new PlanificadorResponse("error", "No hay datos para guardar");
            }
            
            LocalDateTime ahora = LocalDateTime.now();
            
            // Buscar si ya existe un registro
            PlanificadorData entity = repository.findFirstByOrderByIdAsc().orElse(new PlanificadorData());
            
            entity.setDatosJson(datosJson);
            entity.setFechaActualizacion(ahora);
            
            repository.save(entity);
            
            logger.info("Planificador guardado exitosamente a las: {}", ahora);
            logger.info("Tamaño del JSON guardado: {} caracteres", datosJson.length());
            
            PlanificadorResponse response = new PlanificadorResponse("success", "Planificador guardado exitosamente");
            response.setFechaActualizacion(ahora);
            response.setDatos(datosJson);
            
            return response;
            
        } catch (Exception e) {
            logger.error("Error al guardar planificador", e);
            return new PlanificadorResponse("error", "Error al guardar: " + e.getMessage());
        }
    }
    
    /**
     * Carga el planificador guardado
     */
    @Transactional(readOnly = true)
    public PlanificadorResponse cargarPlanificador() {
        try {
            Optional<PlanificadorData> data = repository.findFirstByOrderByIdAsc();
            
            if (data.isPresent()) {
                PlanificadorData entity = data.get();
                
                PlanificadorResponse response = new PlanificadorResponse("success", "Planificador cargado");
                response.setDatos(entity.getDatosJson());
                response.setFechaActualizacion(entity.getFechaActualizacion());
                
                logger.info("Planificador cargado exitosamente");
                return response;
            }
            
            logger.info("No hay planificador guardado");
            return new PlanificadorResponse("empty", "No hay planificador guardado");
            
        } catch (Exception e) {
            logger.error("Error al cargar planificador", e);
            return new PlanificadorResponse("error", "Error al cargar: " + e.getMessage());
        }
    }
    
    /**
     * Elimina el planificador guardado
     */
    @Transactional
    public boolean eliminarPlanificador() {
        try {
            Optional<PlanificadorData> data = repository.findFirstByOrderByIdAsc();
            if (data.isPresent()) {
                repository.delete(data.get());
                logger.info("Planificador eliminado");
                return true;
            }
            logger.warn("No se encontró planificador para eliminar");
            return false;
        } catch (Exception e) {
            logger.error("Error al eliminar planificador", e);
            return false;
        }
    }
}