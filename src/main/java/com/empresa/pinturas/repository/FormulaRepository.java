package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {
    
    List<Formula> findByProductoId(String productoId);
    
    boolean existsByProductoIdAndMateriaPrimaId(String productoId, Long materiaPrimaId);
    
    void deleteByProductoId(String productoId);
}