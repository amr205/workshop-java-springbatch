package com.workshop.torres;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Solo una interfaz: Spring Data JPA genera la implementacion al arrancar.
 * JpaRepository ya trae findAll, findById, save, deleteById, count...
 */
public interface TorreRepository extends JpaRepository<Torre, Long> {

    // SELECT ... FROM torre WHERE nombre = ?
    List<Torre> findByNombre(String nombre);

    // SELECT ... FROM torre WHERE costo <= ?
    List<Torre> findByCostoLessThanEqual(int presupuesto);

    // SELECT ... FROM torre ORDER BY danio DESC LIMIT 1
    Optional<Torre> findFirstByOrderByDanioDesc();
}
