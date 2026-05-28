package com.empresa.pinturas.controller;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.empresa.pinturas.model.Familia;
import com.empresa.pinturas.repository.FamiliaRepository;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin("*")
public class FamiliaController {

    private final FamiliaRepository familiaRepository;

    public FamiliaController(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    @GetMapping
    public List<Familia> getAll() {
        return familiaRepository.findAll();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Familia> getByTipo(@PathVariable String tipo) {
        return familiaRepository.findByTipo(tipo);
    }
}
