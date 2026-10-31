# Paso 11: Middleware: un interceptor para todas las peticiones

Un interceptor registra cada petición a la API, mide cuánto tarda y le agrega un id de rastreo, sin tocar ningún controller.

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
| 11 | `paso-11-middleware` | Middleware: un interceptor para todas las peticiones | ← estás aquí
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización |
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

Un **middleware** es una capa intermedia que intercepta requests y responses antes y después de llegar al endpoint. Sirve para lo que se repite en todos los endpoints: logging, medición de tiempos, ids de correlación y, en el siguiente paso, seguridad.

En Spring MVC se implementa con `HandlerInterceptor`:

| Método | Cuándo se ejecuta |
| --- | --- |
| `preHandle` | **Antes** del controller. Si devuelve `false`, la petición se detiene ahí |
| `postHandle` | Después del controller, antes de escribir la respuesta |
| `afterCompletion` | Al terminar todo, incluso si hubo error |

Y se registra en un `WebMvcConfigurer`, indicando a qué rutas aplica.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `web/RegistroInterceptor.java` | `preHandle`: genera un id, agrega el header `X-Request-Id` y registra la petición. `afterCompletion`: registra el código de estado y los milisegundos |
| `web/WebConfig.java` | Registra el interceptor solo para `/api/**` |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-11-middleware
```

Si ya lo tienes clonado: `git checkout paso-11-middleware`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

Déjala corriendo y abre **otra terminal** en la misma carpeta para las pruebas. Para detenerla: `Ctrl + C`.

### 3. Probar

Los comandos usan `curl`. En **PowerShell escribe `curl.exe`** (con `.exe`); el resto del comando es igual. `-i` muestra el código de estado y los headers de la respuesta. Los cuerpos JSON están en la carpeta `peticiones/` y se envían con `-d "@archivo"`, así no hay problemas con las comillas en Windows.

```bash
curl -i http://localhost:9090/api/v1/torres
curl -i http://localhost:9090/api/v1/torres/99
curl -i http://localhost:9090/hola
```

En la **consola de la aplicación** aparece cada petición a la API:

```
[3f9a1c2e] --> GET /api/v1/torres
[3f9a1c2e] <-- 200 /api/v1/torres en 12 ms
[b71d0e44] --> GET /api/v1/torres/99
[b71d0e44] <-- 404 /api/v1/torres/99 en 3 ms
```

Las respuestas traen el header `X-Request-Id` con el mismo id. `/hola` no aparece en el log: el interceptor solo aplica a `/api/**`.

## Prueba esto

Cambia el `return true` de `preHandle` por `return false` y llama a la API: la respuesta llega vacía porque el interceptor detuvo la petición antes del controller. Así funciona un middleware de seguridad que rechaza peticiones. Regresa a `true`.

## Pregunta para la clase

¿Qué otras cosas pondrías en un middleware en lugar de repetirlas en cada controller?

Siguiente paso: `git checkout paso-12-seguridad`
