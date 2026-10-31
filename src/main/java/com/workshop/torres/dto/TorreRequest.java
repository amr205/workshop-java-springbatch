package com.workshop.torres.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Lo que el cliente ENVIA para crear o reemplazar una torre.
 * No tiene id: lo genera el servidor.
 */
public record TorreRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 30, message = "El nombre no puede tener mas de 30 caracteres")
        String nombre,

        @Positive(message = "El danio debe ser mayor que 0")
        @Max(value = 100, message = "El danio no puede ser mayor que 100")
        int danio,

        @PositiveOrZero(message = "El costo no puede ser negativo")
        int costo) {
}
