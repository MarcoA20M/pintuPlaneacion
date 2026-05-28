package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    @Query("""
        SELECT DISTINCT f
        FROM Familia f
        JOIN Producto p ON p.familia = f
        JOIN p.tipoPintura t
        WHERE LOWER(t.nombre) = LOWER(:tipo)
    """)
    List<Familia> findByTipo(@Param("tipo") String tipo);
}
