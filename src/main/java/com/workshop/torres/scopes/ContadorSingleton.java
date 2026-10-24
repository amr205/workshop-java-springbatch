package com.workshop.torres.scopes;

import org.springframework.stereotype.Component;

/** Scope por defecto: singleton. Una sola instancia compartida por TODOS. */
@Component
public class ContadorSingleton {

    private int valor = 0;

    public int incrementar() {
        return ++valor;
    }
}
