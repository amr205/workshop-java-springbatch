package com.workshop.torres;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

/**
 * API REST de torres: recurso en plural, version en la URL
 * y el verbo lo pone el metodo HTTP, no la URL.
 */
@RestController
@RequestMapping("/api/v1/torres")
public class TorreController {

    private final TorreService torreService;

    public TorreController(TorreService torreService) {
        this.torreService = torreService;
    }

    // GET /api/v1/torres                    -> todas
    // GET /api/v1/torres?nombre=Mago        -> filtro por nombre (query param)
    // GET /api/v1/torres?costoMaximo=250    -> filtro por costo (query param)
    @GetMapping
    public List<Torre> listar(@RequestParam(required = false) String nombre,
                              @RequestParam(required = false) Integer costoMaximo) {
        if (nombre != null) {
            return torreService.buscarPorNombre(nombre);
        }
        if (costoMaximo != null) {
            return torreService.alcanzables(costoMaximo);
        }
        return torreService.listar();
    }

    // GET /api/v1/torres/1  -> 200 con la torre, o 404 si no existe (path param)
    @GetMapping("/{id}")
    public ResponseEntity<Torre> buscar(@PathVariable Long id) {
        return torreService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/torres  -> 201 Created + header Location con la URL del recurso nuevo
    @PostMapping
    public ResponseEntity<Torre> crear(@RequestBody Torre torre) {
        Torre creada = torreService.crear(torre);
        URI ubicacion = URI.create("/api/v1/torres/" + creada.getId());
        return ResponseEntity.created(ubicacion).body(creada);
    }

    // PUT /api/v1/torres/1  -> reemplaza la torre completa
    @PutMapping("/{id}")
    public ResponseEntity<Torre> reemplazar(@PathVariable Long id, @RequestBody Torre torre) {
        return torreService.reemplazar(id, torre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/v1/torres/1/costo  -> cambia solo un campo
    @PatchMapping("/{id}/costo")
    public ResponseEntity<Torre> cambiarCosto(@PathVariable Long id, @RequestBody Map<String, Integer> cambio) {
        return torreService.cambiarCosto(id, cambio.get("costo"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/torres/1  -> 204 No Content (sin body), o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (torreService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/mas-poderosa")
    public Torre masPoderosa() {
        return torreService.masPoderosa();
    }

    @GetMapping("/aleatoria")
    public Torre aleatoria() {
        return torreService.aleatoria();
    }
}
