package com.workshop.torres;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * Capa de negocio: aqui viven las reglas. No sabe de HTTP ni de como se guardan los datos.
 */
@Service
public class TorreService {

    private final TorreRepository torreRepository;
    private final Random random;

    public TorreService(TorreRepository torreRepository, Random random) {
        this.torreRepository = torreRepository;
        this.random = random;
        System.out.println(">> Spring creo un TorreService");
    }

    public List<Torre> listar() {
        return torreRepository.findAll();
    }

    public Torre masPoderosa() {
        return torreRepository.findAll().stream()
                .max(Comparator.comparingInt(Torre::getDanio))
                .orElseThrow(() -> new IllegalStateException("No hay torres"));
    }

    public Torre aleatoria() {
        List<Torre> torres = torreRepository.findAll();
        return torres.get(random.nextInt(torres.size()));
    }
}
