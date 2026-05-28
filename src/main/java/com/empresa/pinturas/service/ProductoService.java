package com.empresa.pinturas.service;

import java.util.List;

import com.empresa.pinturas.dto.ProductoDetalleDTO;
import com.empresa.pinturas.dto.ProductoRequestDTO;

public interface ProductoService {

    ProductoDetalleDTO buscarPorCodigo(String codigo);

    List<ProductoDetalleDTO> buscarPorFamilia(Long familiaId);

    List<ProductoDetalleDTO> buscarPorFamiliaYTipo(Long familiaId, String tipo);

    // ✅ Crear producto
    ProductoDetalleDTO crearProducto(ProductoRequestDTO request);

    // ✅ Actualizar producto - versión simplificada (solo recibe el request)
    ProductoDetalleDTO actualizarProducto(ProductoRequestDTO request);
}