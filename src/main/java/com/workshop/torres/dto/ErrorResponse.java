package com.workshop.torres.dto;

/**
 * Estructura unica para TODOS los errores de la API:
 * codigo legible, mensaje claro, campo afectado, path y timestamp.
 */
public record ErrorResponse(String error, String mensaje, String campo, String path, String timestamp) {
}
