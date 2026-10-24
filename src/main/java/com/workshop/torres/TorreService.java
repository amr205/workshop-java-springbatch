package com.workshop.torres;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TorreService {

    private final List<Torre> torres = List.of(
            new Torre("Arquero", 10, 100),
            new Torre("Canon", 35, 250),
            new Torre("Mago", 25, 200),
            new Torre("Laser", 50, 400));

    public TorreService() {
        // Este mensaje sale UNA vez, al arrancar: lo crea el contenedor, no nosotros
        System.out.println(">> Spring creo un TorreService");
    }

    public List<Torre> listar() {
        return torres;
    }

    public Torre masPoderosa() {
        return torres.stream()
                .max(Comparator.comparingInt(Torre::getDanio))
                .orElseThrow(() -> new IllegalStateException("No hay torres"));
    }
}
