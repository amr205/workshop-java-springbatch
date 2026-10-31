package com.workshop.torres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Prueba unitaria: solo TorreService, sin Spring y sin base de datos.
 * Las dependencias son mocks (objetos simulados) creados por Mockito.
 */
@ExtendWith(MockitoExtension.class)
class TorreServiceTest {

    @Mock
    private TorreRepository torreRepository;

    @Mock
    private Random random;

    @InjectMocks
    private TorreService torreService;   // recibe los mocks por su constructor

    @Test
    void masPoderosaDevuelveLaTorreQueEntregaElRepositorio() {
        // Arrange
        when(torreRepository.findFirstByOrderByDanioDesc())
                .thenReturn(Optional.of(new Torre("Laser", 50, 400)));
        // Act
        Torre resultado = torreService.masPoderosa();
        // Assert
        assertEquals("Laser", resultado.getNombre());
    }

    @Test
    void masPoderosaSinTorresLanzaExcepcion() {
        when(torreRepository.findFirstByOrderByDanioDesc()).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> torreService.masPoderosa());
    }

    @Test
    void aleatoriaUsaElIndiceQueDevuelveRandom() {
        when(torreRepository.findAll()).thenReturn(List.of(
                new Torre("Arquero", 10, 100),
                new Torre("Canon", 35, 250)));
        when(random.nextInt(2)).thenReturn(1);

        assertEquals("Canon", torreService.aleatoria().getNombre());
    }

    @Test
    void alcanzablesConsultaAlRepositorioConElPresupuesto() {
        torreService.alcanzables(250);

        verify(torreRepository).findByCostoLessThanEqual(250);
    }

    @Test
    void eliminarUnaTorreQueNoExisteDevuelveFalse() {
        when(torreRepository.existsById(99L)).thenReturn(false);

        assertFalse(torreService.eliminar(99L));
        verify(torreRepository, never()).deleteById(99L);
    }
}
