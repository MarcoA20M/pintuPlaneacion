package com.empresa.pinturas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.pinturas.model.Proceso;

public interface ProcesoRepositorio extends JpaRepository<Proceso, Long> {

    List<Proceso> findByProducto_IdOrderByPaso(String productoId);
}
