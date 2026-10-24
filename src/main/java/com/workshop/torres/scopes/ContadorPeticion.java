package com.workshop.torres.scopes;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/** Scope request: una instancia por cada peticion HTTP. */
@Component
@RequestScope
public class ContadorPeticion {

    private int valor = 0;

    public int incrementar() {
        return ++valor;
    }
}
