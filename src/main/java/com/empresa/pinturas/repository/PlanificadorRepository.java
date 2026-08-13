// src/main/java/com/empresa/pinturas/repository/PlanificadorRepository.java
package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.PlanificadorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanificadorRepository extends JpaRepository<PlanificadorData, Long> {
    // Solo necesitamos obtener el primero (solo habrá un registro)
    Optional<PlanificadorData> findFirstByOrderByIdAsc();
}