package com.empresa.pinturas.service;

import com.empresa.pinturas.model.Consumo;
import com.empresa.pinturas.repository.ConsumoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    public ConsumoService(ConsumoRepository consumoRepository) {
        this.consumoRepository = consumoRepository;
    }

    public List<Consumo> listarPorMateriaPrima(String materiaPrimaCodigo) {
        return consumoRepository.findByMateriaPrima_Codigo(materiaPrimaCodigo);
    }

    public Double getConsumoTotalDesde(String materiaPrimaCodigo, LocalDateTime desde) {
        return consumoRepository.sumConsumosByMateriaPrimaDesde(materiaPrimaCodigo, desde);
    }
}