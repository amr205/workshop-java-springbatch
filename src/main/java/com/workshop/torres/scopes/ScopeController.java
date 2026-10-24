package com.workshop.torres.scopes;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopeController {

    private final ContadorSingleton singleton;
    private final ContadorPrototype prototypeInyectado;
    private final ObjectProvider<ContadorPrototype> prototypes;
    private final ContadorPeticion peticion;

    public ScopeController(ContadorSingleton singleton,
                           ContadorPrototype prototypeInyectado,
                           ObjectProvider<ContadorPrototype> prototypes,
                           ContadorPeticion peticion) {
        this.singleton = singleton;
        this.prototypeInyectado = prototypeInyectado;
        this.prototypes = prototypes;
        this.peticion = peticion;
    }

    @GetMapping("/scopes")
    public Map<String, Integer> scopes() {
        Map<String, Integer> resultado = new LinkedHashMap<>();

        // Crece con cada llamada: todos comparten la misma instancia
        resultado.put("singleton", singleton.incrementar());

        // TRAMPA: tambien crece. El controller es singleton y el prototype
        // se le inyecto UNA sola vez, al crearlo.
        resultado.put("prototypeInyectado", prototypeInyectado.incrementar());

        // Siempre 1: pedimos una instancia nueva en cada llamada
        resultado.put("prototypeNuevo", prototypes.getObject().incrementar());

        // Siempre 2: misma instancia dentro de la peticion, nueva en la siguiente
        peticion.incrementar();
        resultado.put("peticion", peticion.incrementar());

        return resultado;
    }
}
