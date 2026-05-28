package com.empresa.pinturas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.empresa.pinturas.model.ProductoEnvasado;
import com.empresa.pinturas.model.ProductoEnvasadoId;

import jakarta.transaction.Transactional;

public interface ProductoEnvasadoRepositorio
        extends JpaRepository<ProductoEnvasado, ProductoEnvasadoId> {

    List<ProductoEnvasado> findByProducto_Id(String productoId);
     @Transactional
    @Modifying
    @Query("DELETE FROM ProductoEnvasado pe WHERE pe.producto.id = :productoId")
    void deleteByProductoId(String productoId);
}
