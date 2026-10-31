package com.workshop.torres;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.torres.dto.CambioCostoRequest;
import com.workshop.torres.dto.TorreRequest;
import com.workshop.torres.dto.TorreResponse;

/**
 * El controller solo recibe, valida (@Valid) y delega.
 * Habla en DTOs: nunca recibe ni devuelve la entidad JPA.
 */
@RestController
@RequestMapping("/api/v1/torres")
public class TorreController {

    private final TorreService torreService;

    public TorreController(TorreService torreService) {
        this.torreService = torreService;
    }

    @GetMapping
    public List<TorreResponse> listar(@RequestParam(required = false) String nombre,
                                      @RequestParam(required = false) Integer costoMaximo) {
        List<Torre> torres;
        if (nombre != null) {
            torres = torreService.buscarPorNombre(nombre);
        } else if (costoMaximo != null) {
            torres = torreService.alcanzables(costoMaximo);
        } else {
            torres = torreService.listar();
        }
        return torres.stream().map(TorreResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public TorreResponse buscar(@PathVariable Long id) {
        return TorreResponse.desde(torreService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TorreResponse> crear(@RequestBody @Valid TorreRequest request) {
        Torre creada = torreService.crear(new Torre(request.nombre(), request.danio(), request.costo()));
        URI ubicacion = URI.create("/api/v1/torres/" + creada.getId());
        return ResponseEntity.created(ubicacion).body(TorreResponse.desde(creada));
    }

    @PutMapping("/{id}")
    public TorreResponse reemplazar(@PathVariable Long id, @RequestBody @Valid TorreRequest request) {
        Torre datos = new Torre(request.nombre(), request.danio(), request.costo());
        return TorreResponse.desde(torreService.reemplazar(id, datos));
    }

    @PatchMapping("/{id}/costo")
    public TorreResponse cambiarCosto(@PathVariable Long id, @RequestBody @Valid CambioCostoRequest request) {
        return TorreResponse.desde(torreService.cambiarCosto(id, request.costo()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        torreService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mas-poderosa")
    public TorreResponse masPoderosa() {
        return TorreResponse.desde(torreService.masPoderosa());
    }

    @GetMapping("/aleatoria")
    public TorreResponse aleatoria() {
        return TorreResponse.desde(torreService.aleatoria());
    }
}
