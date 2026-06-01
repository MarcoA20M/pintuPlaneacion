package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    
    List<Consumo> findByLoteId(String loteId);
    
    List<Consumo> findByProductoId(String productoId);
    
    // 🔴 CORREGIDO: Usar materiaPrima.codigo
    List<Consumo> findByMateriaPrima_Codigo(String materiaPrimaCodigo);
    
    List<Consumo> findByFechaConsumoBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT SUM(c.cantidadConsumida) FROM Consumo c WHERE c.materiaPrima.codigo = :materiaCodigo AND c.fechaConsumo >= :fechaInicio")
    Double sumConsumosByMateriaPrimaDesde(@Param("materiaCodigo") String materiaCodigo, @Param("fechaInicio") LocalDateTime fechaInicio);
}