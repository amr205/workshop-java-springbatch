package com.workshop.torres;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.workshop.torres.error.TorreDuplicadaException;
import com.workshop.torres.error.TorreNoEncontradaException;

/**
 * Las reglas de negocio lanzan excepciones; el ManejadorDeErrores
 * las traduce a codigos HTTP. El servicio no sabe nada de HTTP.
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

    public Torre buscarPorId(Long id) {
        return torreRepository.findById(id)
                .orElseThrow(() -> new TorreNoEncontradaException(id));
    }

    public Torre crear(Torre torre) {
        // Regla de negocio: no puede haber dos torres con el mismo nombre
        if (!torreRepository.findByNombre(torre.getNombre()).isEmpty()) {
            throw new TorreDuplicadaException(torre.getNombre());
        }
        torre.setId(null);
        return torreRepository.save(torre);
    }

    public Torre reemplazar(Long id, Torre datos) {
        Torre torre = buscarPorId(id);
        torre.setNombre(datos.getNombre());
        torre.setDanio(datos.getDanio());
        torre.setCosto(datos.getCosto());
        return torreRepository.save(torre);
    }

    public Torre cambiarCosto(Long id, int costo) {
        Torre torre = buscarPorId(id);
        torre.setCosto(costo);
        return torreRepository.save(torre);
    }

    public void eliminar(Long id) {
        if (!torreRepository.existsById(id)) {
            throw new TorreNoEncontradaException(id);
        }
        torreRepository.deleteById(id);
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
