package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    // 🔴 NUEVA CONSULTA - Buscar directamente por el campo 'tipo' de la familia
    @Query("SELECT f FROM Familia f WHERE LOWER(f.tipo) = LOWER(:tipo)")
    List<Familia> findByTipo(@Param("tipo") String tipo);

    // Buscar familias por nombre (ignorando mayúsculas/minúsculas)
    List<Familia> findByNombreContainingIgnoreCase(String nombre);
}