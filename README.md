# Workshop de Spring Boot paso a paso

Una aplicación pequeña con el tema del Tower Defense que crece **un concepto por paso**. Cada paso es una rama con su propio README: qué concepto explica, qué cambió y cómo correrlo desde cero.

- **Pasos 1–8**: fundamentos de Spring Boot (IoC, beans, inyección, capas, scopes, configuración, JPA y pruebas).
- **Pasos 9–10**: diseño de APIs REST (CRUD, códigos HTTP, DTOs, validación y errores).
- **Pasos 11–13**: middleware y seguridad (interceptores, AuthN vs AuthZ, JWT).

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean |
| 5 | `paso-5-scopes` | Scopes de los beans |
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value |
| 7 | `paso-7-jpa` | Spring Data JPA con H2 |
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |
| 9 | `paso-9-api-rest` | Diseño de una API REST (CRUD, códigos HTTP) |
| 10 | `paso-10-dtos-y-errores` | DTOs, validación y errores con estructura |
| 11 | `paso-11-middleware` | Middleware: un interceptor para todas las peticiones |
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización |
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes |

Para empezar:

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-1-spring-boot
```

Requisitos: JDK 17+, Maven 3.9+ y Git.
