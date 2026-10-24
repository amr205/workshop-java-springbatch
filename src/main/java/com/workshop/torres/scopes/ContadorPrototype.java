package com.workshop.torres.scopes;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Scope prototype: Spring crea una instancia NUEVA cada vez que se la piden. */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContadorPrototype {

    private int valor = 0;

    public int incrementar() {
        return ++valor;
    }
}
