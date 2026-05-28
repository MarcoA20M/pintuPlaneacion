package com.empresa.pinturas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.List;

import com.empresa.pinturas.dto.*;
import com.empresa.pinturas.model.*;
import com.empresa.pinturas.repository.*;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepositorio productoRepo;
    private final ProductoEnvasadoRepositorio productoEnvasadoRepo;
    private final ProcesoRepositorio procesoRepo;
    private final TipoPinturaRepositorio tipoPinturaRepo;

    public ProductoServiceImpl(ProductoRepositorio productoRepo,
                               ProductoEnvasadoRepositorio productoEnvasadoRepo,
                               ProcesoRepositorio procesoRepo,
                               TipoPinturaRepositorio tipoPinturaRepo) {
        this.productoRepo = productoRepo;
        this.productoEnvasadoRepo = productoEnvasadoRepo;
        this.procesoRepo = procesoRepo;
        this.tipoPinturaRepo = tipoPinturaRepo;
    }

    // ========== MÉTODOS GET EXISTENTES ==========
    
    @Override
    public ProductoDetalleDTO buscarPorCodigo(String codigo) {
        Producto producto = productoRepo.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDetalleDTO(producto);
    }

    @Override
    public List<ProductoDetalleDTO> buscarPorFamilia(Long familiaId) {
        return productoRepo.findByFamiliaId(familiaId)
                .stream()
                .map(this::mapToDetalleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDetalleDTO> buscarPorFamiliaYTipo(Long familiaId, String tipo) {
        return productoRepo
                .findByFamiliaIdAndTipoPinturaNombreIgnoreCase(familiaId, tipo)
                .stream()
                .map(this::mapToDetalleDTO)
                .collect(Collectors.toList());
    }

    // ========== ✅ NUEVO MÉTODO POST - CREAR PRODUCTO ==========
    
    @Override
    @Transactional
    public ProductoDetalleDTO crearProducto(ProductoRequestDTO request) {
        // Verificar si ya existe
        if (productoRepo.existsById(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + request.getCodigo());
        }
        return guardarProducto(request);
    }

    // ========== ✅ NUEVO MÉTODO PUT - ACTUALIZAR PRODUCTO ==========
    
    @Override
    @Transactional
    public ProductoDetalleDTO actualizarProducto(ProductoRequestDTO request) {
        // Verificar que existe
        if (!productoRepo.existsById(request.getCodigo())) {
            throw new RuntimeException("No existe un producto con el código: " + request.getCodigo());
        }
        return guardarProducto(request);
    }

    // ========== MÉTODO PRIVADO PARA GUARDAR (CREAR O ACTUALIZAR) ==========
    
    private ProductoDetalleDTO guardarProducto(ProductoRequestDTO request) {
        // Obtener producto existente o crear nuevo
        Producto producto = productoRepo.findById(request.getCodigo())
                .orElse(new Producto());

        // Actualizar campos
        producto.setId(request.getCodigo());
        producto.setDescripcion(request.getDescripcion());
        producto.setPoderCubriente(request.getPoderCubriente());

        // Asignar tipo de pintura si viene en el request
        if (request.getTipoPinturaId() != null) {
            TipoPintura tipoPintura = tipoPinturaRepo.findById(request.getTipoPinturaId())
                    .orElseThrow(() -> new RuntimeException("Tipo de pintura no encontrado"));
            producto.setTipoPintura(tipoPintura);
        }

        // Guardar producto
        Producto saved = productoRepo.save(producto);

        // Si vienen envasados en el request, actualizarlos
        if (request.getEnvasados() != null && !request.getEnvasados().isEmpty()) {
            // Eliminar envasados existentes (si es actualización)
            productoEnvasadoRepo.deleteByProductoId(saved.getId());
            
            // Crear nuevos envasados
            for (var envRequest : request.getEnvasados()) {
                ProductoEnvasado productoEnvasado = new ProductoEnvasado();
                productoEnvasado.setProducto(saved);
                productoEnvasado.setArticulo(envRequest.getArticulo());
                
                // Si tienes la entidad Envasado, busca o crea
                // Por ahora asumimos que solo guardamos ProductoEnvasado
                productoEnvasadoRepo.save(productoEnvasado);
            }
        }

        return mapToDetalleDTO(saved);
    }

    // ========== MÉTODO PRIVADO REUTILIZABLE ==========
    
    private ProductoDetalleDTO mapToDetalleDTO(Producto producto) {
        List<EnvasadoDTO> envasados = productoEnvasadoRepo
                .findByProducto_Id(producto.getId())
                .stream()
                .map(pe -> new EnvasadoDTO(
                        pe.getEnvasado() != null ? pe.getEnvasado().getId() : null,
                        pe.getArticulo()
                ))
                .collect(Collectors.toList());

        List<ProcesoDTO> procesos = procesoRepo
                .findByProducto_IdOrderByPaso(producto.getId())
                .stream()
                .map(p -> new ProcesoDTO(
                        p.getPaso(),
                        p.getDescripcion()
                ))
                .collect(Collectors.toList());

        Long tipoId = (producto.getTipoPintura() != null)
                ? producto.getTipoPintura().getId()
                : null;

        return new ProductoDetalleDTO(
                producto.getId(),
                producto.getDescripcion(),
                producto.getPoderCubriente(),
                tipoId,
                envasados,
                procesos
        );
    }
}