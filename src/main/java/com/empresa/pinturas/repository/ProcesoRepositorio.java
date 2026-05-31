package com.empresa.pinturas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.empresa.pinturas.model.Proceso;

public interface ProcesoRepositorio extends JpaRepository<Proceso, Long> {

    List<Proceso> findByProducto_IdOrderByPaso(String productoId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Proceso p WHERE p.producto.id = :productoId")
    void deleteByProductoId(@Param("productoId") String productoId);
}