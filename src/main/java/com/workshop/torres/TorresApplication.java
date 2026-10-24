package com.workshop.torres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TorresApplication {

    public static void main(String[] args) {
        // Arranca el contenedor de Spring y el servidor Tomcat embebido
        SpringApplication.run(TorresApplication.class, args);
    }
}
