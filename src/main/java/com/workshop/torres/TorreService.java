package com.workshop.torres;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

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

    public Optional<Torre> buscarPorId(Long id) {
        return torreRepository.findById(id);
    }

    public Torre crear(Torre torre) {
        torre.setId(null);   // el id lo genera la base de datos
        return torreRepository.save(torre);
    }

    public Optional<Torre> reemplazar(Long id, Torre datos) {
        return torreRepository.findById(id).map(torre -> {
            torre.setNombre(datos.getNombre());
            torre.setDanio(datos.getDanio());
            torre.setCosto(datos.getCosto());
            return torreRepository.save(torre);
        });
    }

    public Optional<Torre> cambiarCosto(Long id, int costo) {
        return torreRepository.findById(id).map(torre -> {
            torre.setCosto(costo);
            return torreRepository.save(torre);
        });
    }

    public boolean eliminar(Long id) {
        if (!torreRepository.existsById(id)) {
            return false;
        }
        torreRepository.deleteById(id);
        return true;
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
