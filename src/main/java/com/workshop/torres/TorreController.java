package com.workshop.torres;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TorreController {

    private final TorreService torreService;

    // Inyeccion por constructor: Spring ve que necesitamos un TorreService
    // y nos pasa el bean que ya tiene creado. No hay new ni @Autowired.
    public TorreController(TorreService torreService) {
        this.torreService = torreService;
    }

    @GetMapping("/torres")
    public List<Torre> listar() {
        return torreService.listar();
    }

    @GetMapping("/torres/mas-poderosa")
    public Torre masPoderosa() {
        return torreService.masPoderosa();
    }
}
