package com.workshop.torres;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Prueba de integracion: levanta el contexto completo de Spring con H2.
 * Comprueba que todos los beans se conectan y que los datos se cargaron.
 */
@SpringBootTest
class TorresApplicationTests {

    @Autowired
    private TorreService torreService;

    @Test
    void elContextoArrancaYLaTorreMasPoderosaEsLaser() {
        assertEquals("Laser", torreService.masPoderosa().getNombre());
    }
}
