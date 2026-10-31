package com.workshop.torres.error;

public class TorreNoEncontradaException extends RuntimeException {

    public TorreNoEncontradaException(Long id) {
        super("No existe una torre con id " + id);
    }
}
