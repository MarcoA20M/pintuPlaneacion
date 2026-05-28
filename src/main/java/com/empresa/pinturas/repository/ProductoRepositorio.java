package com.empresa.pinturas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.pinturas.model.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, String> {
    List<Producto> findByFamiliaId(Long familiaId);

    // 🔹 Productos por familia y tipo de pintura
    List<Producto> findByFamiliaIdAndTipoPinturaNombreIgnoreCase(
            Long familiaId,
            String tipo
    );
}
