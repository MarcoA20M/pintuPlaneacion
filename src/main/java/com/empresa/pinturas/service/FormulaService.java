package com.empresa.pinturas.service;

import com.empresa.pinturas.dto.FormulaRequestDTO;
import com.empresa.pinturas.dto.FormulaResponseDTO;
import com.empresa.pinturas.model.Formula;
import com.empresa.pinturas.model.MateriaPrima;
import com.empresa.pinturas.model.Producto;
import com.empresa.pinturas.repository.FormulaRepository;
import com.empresa.pinturas.repository.MateriaPrimaRepository;
import com.empresa.pinturas.repository.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormulaService {

    private final FormulaRepository formulaRepository;
    private final ProductoRepositorio productoRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public FormulaService(FormulaRepository formulaRepository,
                          ProductoRepositorio productoRepository,
                          MateriaPrimaRepository materiaPrimaRepository) {
        this.formulaRepository = formulaRepository;
        this.productoRepository = productoRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public List<FormulaResponseDTO> listarPorProducto(String productoId) {
        if (productoId == null || productoId.trim().isEmpty()) {
            return List.of();
        }
        return formulaRepository.findByProductoId(productoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormulaResponseDTO crear(FormulaRequestDTO dto) {
        if (dto.getProductoId() == null || dto.getProductoId().trim().isEmpty()) {
            throw new RuntimeException("El ID del producto no puede estar vacío");
        }
        if (dto.getMateriaPrimaId() == null) {
            throw new RuntimeException("Debe seleccionar una materia prima");
        }
        if (dto.getCantidadPorLitro() == null || dto.getCantidadPorLitro() <= 0) {
            throw new RuntimeException("La cantidad por litro debe ser mayor a 0");
        }

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + dto.getProductoId()));

        MateriaPrima materiaPrima = materiaPrimaRepository.findById(dto.getMateriaPrimaId())
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada: " + dto.getMateriaPrimaId()));

        if (formulaRepository.existsByProductoIdAndMateriaPrimaId(dto.getProductoId(), dto.getMateriaPrimaId())) {
            throw new RuntimeException("Esta materia prima ya está asignada al producto");
        }

        Formula formula = new Formula();
        formula.setProducto(producto);
        formula.setMateriaPrima(materiaPrima);
        formula.setCantidadPorLitro(dto.getCantidadPorLitro());

        Formula saved = formulaRepository.save(formula);
        return convertToDTO(saved);
    }

    @Transactional
    public FormulaResponseDTO actualizar(Long id, FormulaRequestDTO dto) {
        if (id == null) {
            throw new RuntimeException("El ID de la fórmula no puede ser nulo");
        }

        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fórmula no encontrada: " + id));

        if (dto.getCantidadPorLitro() != null) {
            formula.setCantidadPorLitro(dto.getCantidadPorLitro());
        }

        Formula saved = formulaRepository.save(formula);
        return convertToDTO(saved);
    }

    @Transactional
    public void eliminar(Long id) {
        if (id == null) {
            throw new RuntimeException("El ID de la fórmula no puede ser nulo");
        }
        if (!formulaRepository.existsById(id)) {
            throw new RuntimeException("Fórmula no encontrada: " + id);
        }
        formulaRepository.deleteById(id);
    }

   private FormulaResponseDTO convertToDTO(Formula formula) {
    FormulaResponseDTO dto = new FormulaResponseDTO();
    dto.setId(formula.getId());
    dto.setProductoId(formula.getProducto().getId());
    dto.setProductoNombre(formula.getProducto().getDescripcion());
    dto.setProductoCodigo(formula.getProducto().getId());
    
    // 🔴 IMPORTANTE: Setear datos de materia prima
    if (formula.getMateriaPrima() != null) {
        dto.setMateriaPrimaId(formula.getMateriaPrima().getId());
        dto.setMateriaPrimaNombre(formula.getMateriaPrima().getNombre());
        dto.setMateriaPrimaCodigo(formula.getMateriaPrima().getCodigo());
        dto.setMateriaPrimaTipo(formula.getMateriaPrima().getTipo());
    }
    
    dto.setCantidadPorLitro(formula.getCantidadPorLitro());
    return dto;
}
}