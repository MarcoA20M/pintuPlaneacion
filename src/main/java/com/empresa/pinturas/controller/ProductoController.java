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

    // ✅ MÉTODO POST - Crear nuevo producto
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


    

    // ✅ MÉTODO PUT - Actualizar producto existente
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable String codigo,
            @RequestBody ProductoRequestDTO request) {
        try {
            request.setCodigo(codigo);
            ProductoDetalleDTO resultado = service.actualizarProducto(request);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }




    
}