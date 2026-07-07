package com.empresa.pinturas.repository;

import com.empresa.pinturas.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperarioRepository extends JpaRepository<Operario, Integer> {
    List<Operario> findByAreaOrderByOrdenVinilicaAsc(String area);
    List<Operario> findByAreaAndActivoTrueOrderByOrdenVinilicaAsc(String area);
    List<Operario> findByAreaAndPuesto(String area, String puesto);
    List<Operario> findByAreaAndActivoTrue(String area);
}