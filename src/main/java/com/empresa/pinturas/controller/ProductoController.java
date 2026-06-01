package com.empresa.pinturas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.pinturas.dto.ProductoDetalleDTO;
import com.empresa.pinturas.dto.ProductoRequestDTO;
import com.empresa.pinturas.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // 🔴 NUEVO: Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDetalleDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{codigo}")
    public ProductoDetalleDTO buscarPorCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @GetMapping("/familia/{familiaId}")
    public List<ProductoDetalleDTO> buscarPorFamilia(@PathVariable Long familiaId) {
        return service.buscarPorFamilia(familiaId);
    }

    @GetMapping("/familia/{familiaId}/tipo/{tipo}")
    public List<ProductoDetalleDTO> buscarPorFamiliaYTipo(
            @PathVariable Long familiaId,
            @PathVariable String tipo
    ) {
        return service.buscarPorFamiliaYTipo(familiaId, tipo);
    }

    // POST - Crear nuevo producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoRequestDTO request) {
        try {
            ProductoDetalleDTO resultado = service.crearProducto(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // PUT - Actualizar producto
    @PutMapping("/{codigoActual}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable String codigoActual,
            @RequestBody ProductoRequestDTO request) {
        try {
            System.out.println("========== CONTROLLER PUT ==========");
            System.out.println("📌 Path variable codigoActual: " + codigoActual);
            System.out.println("📌 Request body codigo: " + request.getCodigo());
            
            ProductoDetalleDTO resultado = service.actualizarProducto(codigoActual, request);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("❌ Error en controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}