package com.empresa.pinturas.service;

import java.util.List;

import com.empresa.pinturas.dto.ProductoDetalleDTO;
import com.empresa.pinturas.dto.ProductoRequestDTO;

public interface ProductoService {

    ProductoDetalleDTO buscarPorCodigo(String codigo);

    List<ProductoDetalleDTO> buscarPorFamilia(Long familiaId);

    List<ProductoDetalleDTO> buscarPorFamiliaYTipo(Long familiaId, String tipo);

    ProductoDetalleDTO crearProducto(ProductoRequestDTO request);

    ProductoDetalleDTO actualizarProducto(String codigoActual, ProductoRequestDTO request);

    // 🔴 SOLO DECLARACIÓN, sin cuerpo
    List<ProductoDetalleDTO> listarTodos();
}