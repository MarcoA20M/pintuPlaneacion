package com.empresa.pinturas.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.empresa.pinturas.model.Envasado;
import com.empresa.pinturas.repository.EnvasadoRepositorio;

@RestController
@RequestMapping("/api/envasados")
@CrossOrigin(origins = "*")
public class EnvasadoController {

    private final EnvasadoRepositorio envasadoRepository;

    public EnvasadoController(EnvasadoRepositorio envasadoRepository) {
        this.envasadoRepository = envasadoRepository;
    }

    @GetMapping
    public List<Envasado> obtenerTodos() {
        return envasadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Envasado obtenerPorId(@PathVariable Integer id) {
        return envasadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envasado no encontrado: " + id));
    }
}