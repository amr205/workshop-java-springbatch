package com.workshop.torres;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * Capa de negocio. Ahora delega las consultas al repositorio JPA.
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
        return torreRepository.findFirstByOrderByDanioDesc()
                .orElseThrow(() -> new IllegalStateException("No hay torres"));
    }

    public List<Torre> alcanzables(int presupuesto) {
        return torreRepository.findByCostoLessThanEqual(presupuesto);
    }

    public List<Torre> buscarPorNombre(String nombre) {
        return torreRepository.findByNombre(nombre);
    }

    public Torre aleatoria() {
        List<Torre> torres = torreRepository.findAll();
        return torres.get(random.nextInt(torres.size()));
    }
}
