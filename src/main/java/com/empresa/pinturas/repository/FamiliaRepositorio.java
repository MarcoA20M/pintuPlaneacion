package com.empresa.pinturas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.pinturas.model.Familia;

public interface FamiliaRepositorio extends JpaRepository<Familia, String> {
}
