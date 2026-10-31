package com.workshop.torres.dto;

import com.workshop.torres.Torre;

/**
 * Lo que la API DEVUELVE. Asi la entidad JPA no se expone directamente:
 * si la tabla cambia, el contrato de la API no tiene que cambiar.
 */
public record TorreResponse(Long id, String nombre, int danio, int costo) {

    public static TorreResponse desde(Torre torre) {
        return new TorreResponse(torre.getId(), torre.getNombre(), torre.getDanio(), torre.getCosto());
    }
}
