package com.workshop.torres;

import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Random es una clase del JDK: no podemos ponerle @Component.
    // Con @Bean le decimos a Spring como crearla y la registramos como bean.
    @Bean
    public Random random() {
        return new Random();
    }

    // Se ejecuta al arrancar: llena la base de datos H2 (en memoria) con 4 torres
    @Bean
    public CommandLineRunner cargarTorres(TorreRepository torreRepository) {
        return args -> {
            if (torreRepository.count() == 0) {
                torreRepository.saveAll(List.of(
                        new Torre("Arquero", 10, 100),
                        new Torre("Canon", 35, 250),
                        new Torre("Mago", 25, 200),
                        new Torre("Laser", 50, 400)));
            }
        };
    }
}
