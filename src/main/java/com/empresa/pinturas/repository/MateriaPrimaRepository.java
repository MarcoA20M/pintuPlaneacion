package com.empresa.pinturas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.empresa.pinturas.model.MateriaPrima;

@Repository
public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {
    
    Optional<MateriaPrima> findByCodigo(String codigo);
    
    List<MateriaPrima> findByTipo(String tipo);
    
    @Query("SELECT m FROM MateriaPrima m WHERE m.nivelActual <= m.umbralCritico")
    List<MateriaPrima> findCriticos();
    
    @Query("SELECT m FROM MateriaPrima m WHERE m.nivelActual > m.umbralCritico AND m.nivelActual <= m.umbralAlerta")
    List<MateriaPrima> findEnAlerta();
    
    @Query("SELECT m FROM MateriaPrima m ORDER BY (m.nivelActual / m.capacidadMaxima) ASC")
    List<MateriaPrima> findAllOrderByPorcentajeAsc();

        
}