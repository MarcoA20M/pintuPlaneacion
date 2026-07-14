// src/main/java/com/empresa/pinturas/service/VacacionService.java
package com.empresa.pinturas.service;

import com.empresa.pinturas.model.Vacacion;
import com.empresa.pinturas.repository.VacacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VacacionService {
    
    @Autowired
    private VacacionRepository vacacionRepository;
    
    // ========== CONSULTAS GENERALES ==========
    public List<Vacacion> findAll() {
        return vacacionRepository.findAll();
    }
    
    public List<Vacacion> findActivas() {
        return vacacionRepository.findByActivoTrue();
    }
    
    public List<Vacacion> findEnCurso() {
        return vacacionRepository.findVacacionesEnCurso();
    }
    
    public List<Vacacion> findFuturas() {
        return vacacionRepository.findVacacionesFuturas();
    }
    
    public List<Integer> findOperariosEnVacacionesHoy() {
        return vacacionRepository.findOperariosEnVacacionesHoy();
    }
    
    // ========== CONSULTAS POR OPERARIO ==========
    public List<Vacacion> findByOperario(Integer operarioId) {
        return vacacionRepository.findByOperarioIdOrderByFechaInicioDesc(operarioId);
    }
    
    public List<Vacacion> findActivasByOperario(Integer operarioId) {
        return vacacionRepository.findByOperarioIdAndActivoTrue(operarioId);
    }
    
    public boolean estaEnVacaciones(Integer operarioId) {
        return vacacionRepository.estaEnVacacionesEnFecha(operarioId, LocalDate.now());
    }
    
    public boolean estaEnVacacionesEnFecha(Integer operarioId, LocalDate fecha) {
        return vacacionRepository.estaEnVacacionesEnFecha(operarioId, fecha);
    }
    
    public Vacacion getVacacionActiva(Integer operarioId) {
        List<Vacacion> vacaciones = vacacionRepository.findVacacionesActivasEnRango(
            operarioId, LocalDate.now(), LocalDate.now()
        );
        return vacaciones.isEmpty() ? null : vacaciones.get(0);
    }
    
    // ========== CRUD ==========
    public Vacacion save(Vacacion vacacion) {
        // Validar superposición
        if (vacacionRepository.existeVacacionEnRango(
                vacacion.getOperarioId(), 
                vacacion.getFechaInicio(), 
                vacacion.getFechaFin())) {
            throw new RuntimeException("El operario ya tiene vacaciones en este período");
        }
        return vacacionRepository.save(vacacion);
    }
    
    public Vacacion update(Vacacion vacacion) {
        if (!vacacionRepository.existsById(vacacion.getId())) {
            throw new RuntimeException("Vacación no encontrada");
        }
        return vacacionRepository.save(vacacion);
    }
    
    public void delete(Integer id) {
        vacacionRepository.deleteById(id);
    }
    
    public Vacacion cancelar(Integer id) {
        Vacacion vacacion = vacacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vacación no encontrada"));
        vacacion.setActivo(false);
        return vacacionRepository.save(vacacion);
    }
    
    public Vacacion activar(Integer id) {
        Vacacion vacacion = vacacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vacación no encontrada"));
        vacacion.setActivo(true);
        return vacacionRepository.save(vacacion);
    }
}