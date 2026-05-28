package com.empresa.pinturas.controller;

import org.springframework.web.bind.annotation.*;
import com.empresa.pinturas.dto.*;
import com.empresa.pinturas.service.CargaService;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cargas")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Reforzamos permisos
public class CargaController {

    private final CargaService cargaService;

    public CargaController(CargaService cargaService) {
        this.cargaService = cargaService;
    }

    @PostMapping
    public List<CargaResponseDTO> registrar(@RequestBody List<CargaRequestDTO> dtos) {
        return cargaService.registrar(dtos);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        cargaService.eliminarYReacomodar(id);
    }

    // Cambiamos /hoy por algo más flexible o agregamos el endpoint de fecha
    @GetMapping("/fecha")
    public List<CargaResponseDTO> obtenerCargasPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        // Asegúrate de tener este método en tu CargaService
        return cargaService.listarCargasPorFecha(fecha); 
    }

    @GetMapping("/hoy")
    public List<CargaResponseDTO> obtenerCargasDeHoy() {
        return cargaService.listarCargasDeHoy();
    }
}