package com.empresa.pinturas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empresa.pinturas.model.Envasado;

public interface EnvasadoRepositorio extends JpaRepository<Envasado, Integer> {
   }