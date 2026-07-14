// src/main/java/com/empresa/pinturas/repository/VacacionRepository.java
package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Vacacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacacionRepository extends JpaRepository<Vacacion, Integer> {
    
    // Vacaciones de un operario ordenadas por fecha descendente
    List<Vacacion> findByOperarioIdOrderByFechaInicioDesc(Integer operarioId);
    
    // Vacaciones activas de un operario
    List<Vacacion> findByOperarioIdAndActivoTrue(Integer operarioId);
    
    // Todas las vacaciones activas
    List<Vacacion> findByActivoTrue();
    
    // Verificar si un operario está de vacaciones en una fecha específica
    @Query("SELECT COUNT(v) > 0 FROM Vacacion v WHERE v.operarioId = :operarioId AND v.activo = true AND :fecha BETWEEN v.fechaInicio AND v.fechaFin")
    boolean estaEnVacacionesEnFecha(@Param("operarioId") Integer operarioId, @Param("fecha") LocalDate fecha);
    
    // Vacaciones activas en un rango de fechas
    @Query("SELECT v FROM Vacacion v WHERE v.operarioId = :operarioId AND v.activo = true AND v.fechaInicio <= :fechaFin AND v.fechaFin >= :fechaInicio")
    List<Vacacion> findVacacionesActivasEnRango(
        @Param("operarioId") Integer operarioId,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );
    
    // Vacaciones en curso hoy
    @Query("SELECT v FROM Vacacion v WHERE v.activo = true AND CURRENT_DATE BETWEEN v.fechaInicio AND v.fechaFin")
    List<Vacacion> findVacacionesEnCurso();
    
    // Vacaciones futuras
    @Query("SELECT v FROM Vacacion v WHERE v.activo = true AND v.fechaInicio > CURRENT_DATE ORDER BY v.fechaInicio ASC")
    List<Vacacion> findVacacionesFuturas();
    
    // Verificar si existe vacación superpuesta
    @Query("SELECT COUNT(v) > 0 FROM Vacacion v WHERE v.operarioId = :operarioId AND v.activo = true AND " +
           "(v.fechaInicio <= :fechaFin AND v.fechaFin >= :fechaInicio)")
    boolean existeVacacionEnRango(
        @Param("operarioId") Integer operarioId,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );
    
    // Operarios que están de vacaciones hoy
    @Query("SELECT DISTINCT v.operarioId FROM Vacacion v WHERE v.activo = true AND CURRENT_DATE BETWEEN v.fechaInicio AND v.fechaFin")
    List<Integer> findOperariosEnVacacionesHoy();
}