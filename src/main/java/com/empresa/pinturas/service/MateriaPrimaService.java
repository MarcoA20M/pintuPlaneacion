// MateriaPrimaService.java
package com.empresa.pinturas.service;

import com.empresa.pinturas.dto.MateriaPrimaDTO;
import com.empresa.pinturas.dto.RegistrarCompraDTO;
import com.empresa.pinturas.model.*;
import com.empresa.pinturas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepo;
    private final MovimientoInventarioRepository movimientoRepo;

    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepo,
            MovimientoInventarioRepository movimientoRepo) {
        this.materiaPrimaRepo = materiaPrimaRepo;
        this.movimientoRepo = movimientoRepo;
    }

    // Obtener todas las materias primas
    public List<MateriaPrimaDTO> listarTodas() {
        return materiaPrimaRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener por tipo
    public List<MateriaPrimaDTO> listarPorTipo(String tipo) {
        return materiaPrimaRepo.findByTipo(tipo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener críticas (nivel <= umbral crítico)
    public List<MateriaPrimaDTO> listarCriticas() {
        return materiaPrimaRepo.findCriticos().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener en alerta
    public List<MateriaPrimaDTO> listarEnAlerta() {
        return materiaPrimaRepo.findEnAlerta().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener resumen para dashboard
    // Obtener resumen para dashboard - CORREGIDO
    public Map<String, Object> getResumenDashboard() {
        long totalTanques = materiaPrimaRepo.count();
        long criticos = materiaPrimaRepo.findCriticos().size();
        long alerta = materiaPrimaRepo.findEnAlerta().size();

        return java.util.Map.of(
                "totalTanques", totalTanques,
                "criticos", criticos,
                "alerta", alerta,
                "normales", totalTanques - criticos - alerta);
    }

    // Registrar compra
    @Transactional
    public MateriaPrimaDTO registrarCompra(RegistrarCompraDTO dto) {
        MateriaPrima mp = materiaPrimaRepo.findById(dto.getMateriaPrimaId())
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));

        Double cantidadAntes = mp.getNivelActual();
        Double cantidadDespues = cantidadAntes + dto.getCantidad();

        // Actualizar nivel
        mp.setNivelActual(cantidadDespues);
        materiaPrimaRepo.save(mp);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario(
                mp,
                MovimientoInventario.TipoMovimiento.COMPRA,
                dto.getCantidad(),
                cantidadAntes,
                cantidadDespues,
                dto.getDocumentoReferencia(),
                dto.getUsuario());
        movimiento.setObservaciones(dto.getObservaciones());
        movimientoRepo.save(movimiento);

        return convertToDTO(mp);
    }

    // Obtener una materia prima por ID
    public MateriaPrimaDTO obtenerPorId(Long id) {
        MateriaPrima mp = materiaPrimaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));
        return convertToDTO(mp);
    }

    // Obtener historial de movimientos
    public List<MovimientoInventario> obtenerMovimientos(Long materiaPrimaId) {
        return movimientoRepo.findByMateriaPrimaIdOrderByFechaMovimientoDesc(materiaPrimaId);
    }

    private MateriaPrimaDTO convertToDTO(MateriaPrima mp) {
        MateriaPrimaDTO dto = new MateriaPrimaDTO();
        dto.setId(mp.getId());
        dto.setNombre(mp.getNombre());
        dto.setTipo(mp.getTipo());
        dto.setCodigo(mp.getCodigo());
        dto.setCapacidadMaxima(mp.getCapacidadMaxima());
        dto.setNivelActual(mp.getNivelActual());
        dto.setUnidad(mp.getUnidad());
        dto.setUmbralCritico(mp.getUmbralCritico());
        dto.setUmbralAlerta(mp.getUmbralAlerta());
        dto.setUbicacion(mp.getUbicacion());
        dto.setPorcentajeLlenado(mp.getPorcentajeLlenado());
        dto.setCritico(mp.isCritico());
        dto.setAlerta(mp.isAlerta());
        return dto;
    }

    // En MateriaPrimaService.java - Agregar este método

    // Crear nueva materia prima
    @Transactional
    public MateriaPrimaDTO crearMateriaPrima(MateriaPrimaDTO dto) {
        // Validar que no exista una materia prima con el mismo código
        if (materiaPrimaRepo.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("Ya existe una materia prima con el código: " + dto.getCodigo());
        }

        MateriaPrima mp = new MateriaPrima();
        mp.setCodigo(dto.getCodigo());
        mp.setNombre(dto.getNombre());
        mp.setTipo(dto.getTipo());
        mp.setCapacidadMaxima(dto.getCapacidadMaxima());
        mp.setNivelActual(dto.getNivelActual() != null ? dto.getNivelActual() : 0.0);
        mp.setUnidad(dto.getUnidad() != null ? dto.getUnidad() : "L");
        mp.setUmbralCritico(dto.getUmbralCritico());
        mp.setUmbralAlerta(dto.getUmbralAlerta());
        mp.setCostoPorUnidad(dto.getCostoPorUnidad());
        mp.setUbicacion(dto.getUbicacion());
        mp.setCreatedAt(LocalDateTime.now());
        mp.setUpdatedAt(LocalDateTime.now());

        MateriaPrima saved = materiaPrimaRepo.save(mp);
        return convertToDTO(saved);
    }

    // En MateriaPrimaService.java - Agregar estos métodos

    // Actualizar materia prima
    @Transactional
    public MateriaPrimaDTO actualizarMateriaPrima(Long id, MateriaPrimaDTO dto) {
        MateriaPrima mp = materiaPrimaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));

        // Validar que no exista otra materia prima con el mismo código (excepto la
        // actual)
        if (!mp.getCodigo().equals(dto.getCodigo()) &&
                materiaPrimaRepo.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("Ya existe una materia prima con el código: " + dto.getCodigo());
        }

        mp.setCodigo(dto.getCodigo());
        mp.setNombre(dto.getNombre());
        mp.setTipo(dto.getTipo());
        mp.setCapacidadMaxima(dto.getCapacidadMaxima());
        mp.setNivelActual(dto.getNivelActual() != null ? dto.getNivelActual() : mp.getNivelActual());
        mp.setUnidad(dto.getUnidad() != null ? dto.getUnidad() : "L");
        mp.setUmbralCritico(dto.getUmbralCritico());
        mp.setUmbralAlerta(dto.getUmbralAlerta());
        mp.setCostoPorUnidad(dto.getCostoPorUnidad());
        mp.setUbicacion(dto.getUbicacion());

        MateriaPrima saved = materiaPrimaRepo.save(mp);
        return convertToDTO(saved);
    }

    // Eliminar materia prima
    @Transactional
    public void eliminarMateriaPrima(Long id) {
        MateriaPrima mp = materiaPrimaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));

        // Verificar si tiene fórmulas asociadas antes de eliminar
        if (mp.getFormulas() != null && !mp.getFormulas().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la materia prima porque tiene fórmulas asociadas");
        }

        materiaPrimaRepo.delete(mp);
    }

}