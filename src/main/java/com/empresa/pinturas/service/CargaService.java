package com.empresa.pinturas.service;

import org.springframework.stereotype.Service;
import com.empresa.pinturas.dto.*;
import com.empresa.pinturas.model.*;
import com.empresa.pinturas.repository.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargaService {

    private final CargaRepositorio cargaRepo;
    private final ProductoRepositorio productoRepo;
    private final EnvasadoRepositorio envasadoRepo;

    public CargaService(CargaRepositorio cargaRepo,
                        ProductoRepositorio productoRepo,
                        EnvasadoRepositorio envasadoRepo) {
        this.cargaRepo = cargaRepo;
        this.productoRepo = productoRepo;
        this.envasadoRepo = envasadoRepo;
    }

    @Transactional
    public List<CargaResponseDTO> registrar(List<CargaRequestDTO> dtos) {
        return dtos.stream().map(dto -> {
            Producto producto = productoRepo.findById(dto.getCodigoProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + dto.getCodigoProducto()));

            Envasado envasado = envasadoRepo.findById(dto.getEnvasadoId())
                    .orElseThrow(() -> new RuntimeException("Envasado no encontrado ID: " + dto.getEnvasadoId()));

            Carga carga = new Carga();
            carga.setProducto(producto);
            carga.setEnvasado(envasado);
            carga.setCantidad(dto.getCantidad());
            carga.setLitros(dto.getLitros());
            carga.setTipo(dto.getTipo());
            carga.setFolio(dto.getFolio());
            carga.setFolioHija(dto.getFolioHija());
            carga.setOperario(dto.getOperario());
            carga.setMaquina(dto.getMaquina());
            
            // Si no trae fecha del front, asignamos la actual
            if (carga.getFecha() == null) {
                carga.setFecha(LocalDateTime.now());
            }

            if (carga.getFolio() == null || carga.getFolio().isEmpty()) {
                int siguiente = calcularSiguienteConsecutivo(dto.getTipo());
                carga.setFolio(generarFormatoLote(dto.getTipo(), siguiente));
            }

            carga = cargaRepo.save(carga);

            return mapToResponseDTO(carga);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void eliminarYReacomodar(Long id) {
        Carga cargaABorrar = cargaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la carga"));
        cargaRepo.delete(cargaABorrar);
    }

    // --- NUEVO MÉTODO PARA FILTRAR POR CUALQUIER FECHA (RESUELVE EL ERROR) ---
 public List<CargaResponseDTO> listarCargasPorFecha(LocalDate fecha) {
    // Esto crea el rango: 2026-03-21 00:00:00 hasta 2026-03-21 23:59:59
    LocalDateTime inicio = fecha.atStartOfDay();
    LocalDateTime fin = fecha.atTime(LocalTime.MAX);
    
    // El repositorio ya tiene findByFechaBetween, así que esto funcionará
    return cargaRepo.findByFechaBetween(inicio, fin).stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
}

    public List<CargaResponseDTO> listarCargasDeHoy() {
        return listarCargasPorFecha(LocalDate.now());
    }

    // --- MÉTODOS AUXILIARES ---

    private CargaResponseDTO mapToResponseDTO(Carga c) {
        return new CargaResponseDTO(
                c.getId(),
                c.getProducto().getId(),
                c.getEnvasado().getId(),
                c.getLitros(),
                c.getFolio(),
                c.getCantidad(),
                c.getFolioHija()
                // Si tu DTO soporta operario/maquina, agrégalos aquí
        );
    }

    private int calcularSiguienteConsecutivo(String tipo) {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDate.now().atTime(LocalTime.MAX);
        return (int) cargaRepo.countByTipoAndFechaBetween(tipo, inicio, fin) + 1;
    }

    private String generarFormatoLote(String tipo, int num) {
        LocalDate hoy = LocalDate.now();
        String anio = String.valueOf(hoy.getYear()).substring(2);
        String mes = String.format("%02d", hoy.getMonthValue());
        String dia = String.format("%02d", hoy.getDayOfMonth());
        String seq = String.format("%03d", num);
        return (tipo.toLowerCase().substring(0,1) + anio + mes + dia + seq);
    }
}