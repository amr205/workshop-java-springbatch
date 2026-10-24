package com.workshop.torres;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JuegoController {

    private final TorreService torreService;
    private final String nombreJuego;
    private final int presupuestoInicial;

    // @Value lee valores de application.properties (tambien funciona sobre un campo)
    public JuegoController(TorreService torreService,
                           @Value("${juego.nombre}") String nombreJuego,
                           @Value("${juego.presupuesto-inicial}") int presupuestoInicial) {
        this.torreService = torreService;
        this.nombreJuego = nombreJuego;
        this.presupuestoInicial = presupuestoInicial;
    }

    @GetMapping("/juego")
    public Map<String, Object> juego() {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("nombre", nombreJuego);
        respuesta.put("presupuestoInicial", presupuestoInicial);
        respuesta.put("torresAlcanzables", torreService.alcanzables(presupuestoInicial));
        return respuesta;
    }
}
