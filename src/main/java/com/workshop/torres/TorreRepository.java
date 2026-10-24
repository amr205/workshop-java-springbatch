package com.workshop.torres;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Capa de datos. Por ahora guarda las torres en memoria;
 * en el paso 7 se cambia por una base de datos real con JPA.
 */
@Repository
public class TorreRepository {

    private final List<Torre> torres = List.of(
            new Torre("Arquero", 10, 100),
            new Torre("Canon", 35, 250),
            new Torre("Mago", 25, 200),
            new Torre("Laser", 50, 400));

    public List<Torre> findAll() {
        return torres;
    }
}
