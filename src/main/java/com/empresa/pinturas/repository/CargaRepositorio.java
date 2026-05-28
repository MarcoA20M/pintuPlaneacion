package com.empresa.pinturas.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.pinturas.model.Carga;

public interface CargaRepositorio extends JpaRepository<Carga, Long> {

    // ✅ Este es el que te está faltando para el listado general del día
    List<Carga> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Los que ya tenías para el reacomodo y conteo
    List<Carga> findByTipoAndFechaBetweenOrderByIdAsc(String tipo, LocalDateTime inicio, LocalDateTime fin);
    
    long countByTipoAndFechaBetween(String tipo, LocalDateTime inicio, LocalDateTime fin);
}