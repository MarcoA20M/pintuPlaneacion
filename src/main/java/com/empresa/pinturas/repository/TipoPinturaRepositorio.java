package com.empresa.pinturas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.pinturas.model.TipoPintura;

public interface TipoPinturaRepositorio extends JpaRepository<TipoPintura, Long> {
    // Long porque el ID de TipoPintura es Long
}