package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    
    List<MovimientoInventario> findByMateriaPrimaIdOrderByFechaMovimientoDesc(Long materiaPrimaId);
    
    List<MovimientoInventario> findByTipo(MovimientoInventario.TipoMovimiento tipo);
}