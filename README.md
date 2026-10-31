# Paso 9: Diseño de una API REST (CRUD, códigos HTTP)

Las rutas sueltas (`/torres`, `/torres/aleatoria`) se convierten en una API REST bien diseñada, con CRUD completo y los códigos HTTP correctos.

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
| 9 | `paso-9-api-rest` | Diseño de una API REST (CRUD, códigos HTTP) | ← estás aquí
| 10 | `paso-10-dtos-y-errores` | DTOs, validación y errores con estructura |
| 11 | `paso-11-middleware` | Middleware: un interceptor para todas las peticiones |
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización |
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

Reglas de diseño del curso de APIs aplicadas aquí:

- **Recursos como sustantivos en plural**: `/torres`, nunca `/getTorres` ni `/crearTorre`. El **verbo lo pone el método HTTP**.
- **Versión desde el inicio**: `/api/v1/...`, para evolucionar sin romper a los clientes.
- **Path param** para identificar un recurso (`/torres/3`); **query param** para filtrar (`?costoMaximo=250`); **body JSON** para enviar datos.
- **El código de estado correcto** para cada resultado.

| Método | Endpoint | Qué hace | Éxito | Error |
| --- | --- | --- | --- | --- |
| GET | `/api/v1/torres` | Listar (filtros opcionales `?nombre=` y `?costoMaximo=`) | 200 | — |
| GET | `/api/v1/torres/{id}` | Consultar una | 200 | 404 |
| POST | `/api/v1/torres` | Crear | **201** + header `Location` | — |
| PUT | `/api/v1/torres/{id}` | Reemplazar completa | 200 | 404 |
| PATCH | `/api/v1/torres/{id}/costo` | Cambiar solo el costo | 200 | 404 |
| DELETE | `/api/v1/torres/{id}` | Eliminar | **204** sin body | 404 |

`ResponseEntity` es lo que permite elegir el código de estado y los headers de la respuesta.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `TorreController.java` | Ahora en `@RequestMapping("/api/v1/torres")` con `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`, `@PathVariable`, `@RequestParam`, `@RequestBody` y `ResponseEntity` |
| `TorreService.java` | Nuevos `buscarPorId`, `crear`, `reemplazar`, `cambiarCosto`, `eliminar` |
| `TorreServiceTest.java` | Nueva prueba: eliminar una torre que no existe |
| `peticiones/*.json` | Cuerpos de ejemplo para POST, PUT y PATCH |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-9-api-rest
```

Si ya lo tienes clonado: `git checkout paso-9-api-rest`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

Déjala corriendo y abre **otra terminal** en la misma carpeta para las pruebas. Para detenerla: `Ctrl + C`.

### 3. Probar

Los comandos usan `curl`. En **PowerShell escribe `curl.exe`** (con `.exe`); el resto del comando es igual. `-i` muestra el código de estado y los headers de la respuesta. Los cuerpos JSON están en la carpeta `peticiones/` y se envían con `-d "@archivo"`, así no hay problemas con las comillas en Windows.

```bash
curl -i http://localhost:9090/api/v1/torres
curl -i "http://localhost:9090/api/v1/torres?costoMaximo=250"
curl -i http://localhost:9090/api/v1/torres/1
curl -i http://localhost:9090/api/v1/torres/99                                                               # 404
curl -i -X POST -H "Content-Type: application/json" -d "@peticiones/nueva-torre.json" http://localhost:9090/api/v1/torres         # 201 + Location
curl -i -X PUT -H "Content-Type: application/json" -d "@peticiones/reemplazo-torre.json" http://localhost:9090/api/v1/torres/2
curl -i -X PATCH -H "Content-Type: application/json" -d "@peticiones/cambio-costo.json" http://localhost:9090/api/v1/torres/3/costo
curl -i -X DELETE http://localhost:9090/api/v1/torres/1                                                      # 204
curl -i -X DELETE http://localhost:9090/api/v1/torres/1                                                      # 404: ya no existe
```

Fíjate en la primera línea de cada respuesta (`HTTP/1.1 201`, `204`, `404`…) y en el header `Location` del POST.

## Lo que todavía está mal (se arregla en el paso 10)

1. La API recibe y devuelve la **entidad JPA** (`Torre`) directamente: expone el modelo interno.
2. No hay validación. Prueba `curl -i -X POST -H "Content-Type: application/json" -d "@peticiones/torre-invalida.json" http://localhost:9090/api/v1/torres`: crea una torre sin nombre y con daño negativo.
3. El 404 llega **sin body**: el cliente no sabe qué pasó.

## Pregunta para la clase

¿Por qué `POST /api/v1/torres` es mejor que `POST /crearTorre`? ¿Cuándo usas PUT y cuándo PATCH?

Siguiente paso: `git checkout paso-10-dtos-y-errores`
