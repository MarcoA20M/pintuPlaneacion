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
    private final FamiliaRepository familiaRepo;
    private final EnvasadoRepositorio envasadoRepo;  // ← AGREGADO

    public ProductoServiceImpl(ProductoRepositorio productoRepo,
                               ProductoEnvasadoRepositorio productoEnvasadoRepo,
                               ProcesoRepositorio procesoRepo,
                               TipoPinturaRepositorio tipoPinturaRepo,
                               FamiliaRepository familiaRepo,
                               EnvasadoRepositorio envasadoRepo) {  // ← AGREGADO
        this.productoRepo = productoRepo;
        this.productoEnvasadoRepo = productoEnvasadoRepo;
        this.procesoRepo = procesoRepo;
        this.tipoPinturaRepo = tipoPinturaRepo;
        this.familiaRepo = familiaRepo;
        this.envasadoRepo = envasadoRepo;  // ← AGREGADO
    }

    // ========== MÉTODOS GET ==========

    @Override
    public ProductoDetalleDTO buscarPorCodigo(String codigo) {
        Producto producto = productoRepo.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + codigo));
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
        return productoRepo.findByFamiliaIdAndTipoPinturaNombreIgnoreCase(familiaId, tipo)
                .stream()
                .map(this::mapToDetalleDTO)
                .collect(Collectors.toList());
    }

    // ========== POST - CREAR PRODUCTO ==========

    @Override
    @Transactional
    public ProductoDetalleDTO crearProducto(ProductoRequestDTO request) {
        if (productoRepo.existsById(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + request.getCodigo());
        }
        return guardarProducto(request);
    }

    // ========== PUT - ACTUALIZAR PRODUCTO ==========

    @Override
    @Transactional
    public ProductoDetalleDTO actualizarProducto(String codigoActual, ProductoRequestDTO request) {
        String nuevoCodigo = request.getCodigo();
        
        System.out.println("========== ACTUALIZAR PRODUCTO ==========");
        System.out.println("📌 Código actual (URL): " + codigoActual);
        System.out.println("📌 Nuevo código (body): " + nuevoCodigo);
        System.out.println("📌 Familia ID recibida: " + request.getFamiliaId());
        System.out.println("📌 Descripción: " + request.getDescripcion());

        if (!productoRepo.existsById(codigoActual)) {
            System.err.println("❌ ERROR: No existe un producto con el código: " + codigoActual);
            throw new RuntimeException("No existe un producto con el código: " + codigoActual);
        }
        
        System.out.println("✅ Producto original encontrado: " + codigoActual);

        if (!codigoActual.equals(nuevoCodigo)) {
            System.out.println("🔄 El código ha cambiado de " + codigoActual + " a " + nuevoCodigo);
            
            if (productoRepo.existsById(nuevoCodigo)) {
                System.err.println("❌ ERROR: Ya existe un producto con el código: " + nuevoCodigo);
                throw new RuntimeException("Ya existe un producto con el código: " + nuevoCodigo);
            }

            Producto productoActual = productoRepo.findById(codigoActual)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            Producto nuevoProducto = new Producto();
            nuevoProducto.setId(nuevoCodigo);
            nuevoProducto.setDescripcion(request.getDescripcion() != null ? request.getDescripcion() : productoActual.getDescripcion());
            nuevoProducto.setPoderCubriente(request.getPoderCubriente() != null ? request.getPoderCubriente() : productoActual.getPoderCubriente());
            nuevoProducto.setTipoPintura(productoActual.getTipoPintura());
            nuevoProducto.setColor(request.getColor() != null ? request.getColor() : productoActual.getColor());

            Long familiaId = request.getFamiliaId();
            if (familiaId != null) {
                Familia familia = familiaRepo.findById(familiaId)
                        .orElseThrow(() -> new RuntimeException("Familia no encontrada: " + familiaId));
                nuevoProducto.setFamilia(familia);
            } else if (productoActual.getFamilia() != null) {
                nuevoProducto.setFamilia(productoActual.getFamilia());
            }

            productoRepo.save(nuevoProducto);

            // Copiar envasados
            List<ProductoEnvasado> envasadosActuales = productoEnvasadoRepo.findByProducto_Id(codigoActual);
            for (ProductoEnvasado pe : envasadosActuales) {
                ProductoEnvasado nuevoPE = new ProductoEnvasado();
                nuevoPE.setProducto(nuevoProducto);
                nuevoPE.setArticulo(pe.getArticulo());
                nuevoPE.setDescripcion(pe.getDescripcion());
                if (pe.getEnvasado() != null) {
                    nuevoPE.setEnvasado(pe.getEnvasado());
                }
                productoEnvasadoRepo.save(nuevoPE);
            }

            productoEnvasadoRepo.deleteByProductoId(codigoActual);
            productoRepo.deleteById(codigoActual);
            
            return mapToDetalleDTO(nuevoProducto);
        } else {
            System.out.println("🔄 Mismo código - actualización normal");
            return guardarProducto(request);
        }
    }

    // ========== MÉTODO PRIVADO PARA GUARDAR (CON ENVASADOS) ==========

    private ProductoDetalleDTO guardarProducto(ProductoRequestDTO request) {
        System.out.println("========== GUARDAR PRODUCTO ==========");
        System.out.println("📌 Código: " + request.getCodigo());
        System.out.println("📌 Color: " + request.getColor());
        System.out.println("📌 Envasados recibidos: " + (request.getEnvasados() != null ? request.getEnvasados().size() : 0));

        Producto producto = productoRepo.findById(request.getCodigo())
                .orElse(new Producto());

        producto.setId(request.getCodigo());
        producto.setDescripcion(request.getDescripcion());
        producto.setPoderCubriente(request.getPoderCubriente());
        
        // ✅ ASIGNAR COLOR
        if (request.getColor() != null && !request.getColor().isEmpty()) {
            producto.setColor(request.getColor());
        } else {
            producto.setColor("BLANCO");
        }

        if (request.getTipoPinturaId() != null) {
            TipoPintura tipoPintura = tipoPinturaRepo.findById(request.getTipoPinturaId())
                    .orElseThrow(() -> new RuntimeException("Tipo de pintura no encontrado"));
            producto.setTipoPintura(tipoPintura);
        }

        if (request.getFamiliaId() != null) {
            Familia familia = familiaRepo.findById(request.getFamiliaId())
                    .orElseThrow(() -> new RuntimeException("Familia no encontrada: " + request.getFamiliaId()));
            producto.setFamilia(familia);
        }

        Producto saved = productoRepo.save(producto);
        System.out.println("✅ Producto guardado: " + saved.getId());
        
        // ✅ MANEJAR ENVASADOS
        // Eliminar envasados existentes (para actualización)
        productoEnvasadoRepo.deleteByProductoId(saved.getId());
        
        // Crear nuevos envasados
        if (request.getEnvasados() != null && !request.getEnvasados().isEmpty()) {
            System.out.println("📦 Creando " + request.getEnvasados().size() + " envasados...");
            
            for (var envRequest : request.getEnvasados()) {
                System.out.println("   - EnvasadoId: " + envRequest.getEnvasadoId() + ", Articulo: " + envRequest.getArticulo());
                
                // Buscar el Envasado por ID
                Envasado envasado = envasadoRepo.findById(envRequest.getEnvasadoId())
                        .orElseThrow(() -> new RuntimeException("Envasado no encontrado: " + envRequest.getEnvasadoId()));
                
                ProductoEnvasado productoEnvasado = new ProductoEnvasado();
                productoEnvasado.setProducto(saved);
                productoEnvasado.setEnvasado(envasado);
                productoEnvasado.setArticulo(envRequest.getArticulo());
                productoEnvasado.setDescripcion(envRequest.getDescripcion());
                
                productoEnvasadoRepo.save(productoEnvasado);
            }
            System.out.println("✅ Envasados guardados correctamente");
        } else {
            System.out.println("⚠️ No se recibieron envasados en el request");
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
                        pe.getArticulo()))
                .collect(Collectors.toList());

        List<ProcesoDTO> procesos = procesoRepo
                .findByProducto_IdOrderByPaso(producto.getId())
                .stream()
                .map(p -> new ProcesoDTO(
                        p.getPaso(),
                        p.getDescripcion()))
                .collect(Collectors.toList());

        Long tipoId = (producto.getTipoPintura() != null)
                ? producto.getTipoPintura().getId()
                : null;

        Long familiaId = (producto.getFamilia() != null)
                ? producto.getFamilia().getId()
                : null;

        return new ProductoDetalleDTO(
                producto.getId(),
                producto.getDescripcion(),
                producto.getPoderCubriente(),
                tipoId,
                familiaId,
                envasados,
                procesos);
    }
}