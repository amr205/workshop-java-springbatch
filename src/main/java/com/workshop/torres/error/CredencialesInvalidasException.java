package com.workshop.torres.error;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Usuario o contrasena incorrectos");
    }
}
