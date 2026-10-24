package com.workshop.torres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TorresApplication {

    public static void main(String[] args) {
        // run(...) devuelve el contenedor IoC (ApplicationContext)
        ApplicationContext contexto = SpringApplication.run(TorresApplication.class, args);

        System.out.println("== Beans creados por Spring en nuestro paquete ==");
        for (String nombre : contexto.getBeanDefinitionNames()) {
            Class<?> tipo = contexto.getType(nombre);
            if (tipo != null && tipo.getPackageName().startsWith("com.workshop")) {
                System.out.println(" - " + nombre + " -> " + tipo.getSimpleName());
            }
        }

        // Le pedimos un bean al contenedor en lugar de hacer new
        TorreService servicio = contexto.getBean(TorreService.class);
        System.out.println("Torre mas poderosa: " + servicio.masPoderosa());

        TorreService otraVez = contexto.getBean(TorreService.class);
        System.out.println("Es la misma instancia? " + (servicio == otraVez));
    }
}
