package com.workshop.torres.error;

public class TorreDuplicadaException extends RuntimeException {

    public TorreDuplicadaException(String nombre) {
        super("Ya existe una torre llamada " + nombre);
    }
}
