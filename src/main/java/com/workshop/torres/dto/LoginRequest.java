package com.workshop.torres.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El usuario es obligatorio") String usuario,
        @NotBlank(message = "La contrasena es obligatoria") String password) {
}
