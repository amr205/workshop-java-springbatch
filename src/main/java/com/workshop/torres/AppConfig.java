package com.workshop.torres;

import java.util.Random;

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
}
