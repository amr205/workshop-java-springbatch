# Paso 10: DTOs, validación y errores con estructura

La API deja de exponer la entidad, valida lo que recibe y responde cada error con un JSON consistente.

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
| 10 | `paso-10-dtos-y-errores` | DTOs, validación y errores con estructura | ← estás aquí
| 11 | `paso-11-middleware` | Middleware: un interceptor para todas las peticiones |
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización |
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

**DTO (Data Transfer Object)**: objetos que definen el contrato de la API, separados de la entidad de la base de datos.

- `TorreRequest`: lo que el cliente **envía** (sin `id`).
- `TorreResponse`: lo que la API **devuelve**.

Si mañana la tabla cambia, el contrato no tiene por qué cambiar. Es la regla "no expongas el modelo interno de la base de datos".

**Validación**: anotaciones como `@NotBlank`, `@Positive` y `@Max` en el DTO, y `@Valid` en el controller. Si algo no cumple, Spring rechaza la petición antes de llegar al servicio.

**Manejo centralizado de errores**: el servicio lanza excepciones de negocio (`TorreNoEncontradaException`, `TorreDuplicadaException`) y un `@RestControllerAdvice` las traduce a códigos HTTP con un cuerpo uniforme:

```json
{
  "error": "BAD_REQUEST",
  "mensaje": "El nombre es obligatorio",
  "campo": "nombre",
  "path": "/api/v1/torres",
  "timestamp": "2026-10-30T19:30:00Z"
}
```

| Situación | Excepción | Código |
| --- | --- | --- |
| Campo inválido | `MethodArgumentNotValidException` (la lanza `@Valid`) | 400 |
| JSON mal formado | `HttpMessageNotReadableException` | 400 |
| La torre no existe | `TorreNoEncontradaException` | 404 |
| Ya existe una torre con ese nombre | `TorreDuplicadaException` | 409 |

La regla "el nombre debe ser único" es **lógica de negocio**: vive en el servicio, no en el controller.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `pom.xml` | Nuevo `spring-boot-starter-validation` |
| `dto/TorreRequest.java`, `dto/TorreResponse.java`, `dto/CambioCostoRequest.java`, `dto/ErrorResponse.java` | Nuevos records |
| `error/TorreNoEncontradaException.java`, `error/TorreDuplicadaException.java` | Excepciones de negocio |
| `error/ManejadorDeErrores.java` | `@RestControllerAdvice` con un `@ExceptionHandler` por tipo de error |
| `TorreService.java` | Lanza excepciones en lugar de devolver `Optional`; valida nombre único |
| `TorreController.java` | Usa DTOs y `@Valid`; ya no decide los 404 a mano y queda más corto |
| `TorreServiceTest.java` | Pruebas de 404 y 409 |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-10-dtos-y-errores
```

Si ya lo tienes clonado: `git checkout paso-10-dtos-y-errores`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

Déjala corriendo y abre **otra terminal** en la misma carpeta para las pruebas. Para detenerla: `Ctrl + C`.

### 3. Probar

Los comandos usan `curl`. En **PowerShell escribe `curl.exe`** (con `.exe`); el resto del comando es igual. `-i` muestra el código de estado y los headers de la respuesta. Los cuerpos JSON están en la carpeta `peticiones/` y se envían con `-d "@archivo"`, así no hay problemas con las comillas en Windows.

```bash
curl -i -X POST -H "Content-Type: application/json" -d "@peticiones/nueva-torre.json" http://localhost:9090/api/v1/torres       # 201
curl -i -X POST -H "Content-Type: application/json" -d "@peticiones/nueva-torre.json" http://localhost:9090/api/v1/torres       # 409: el nombre ya existe
curl -i -X POST -H "Content-Type: application/json" -d "@peticiones/torre-invalida.json" http://localhost:9090/api/v1/torres    # 400: campo nombre
curl -i -X POST -H "Content-Type: application/json" -d "esto no es json" http://localhost:9090/api/v1/torres                    # 400: JSON inválido
curl -i http://localhost:9090/api/v1/torres/99                                                             # 404 con cuerpo JSON
```

### 4. Pruebas automáticas

```bash
mvn test
```

Deben pasar 7 pruebas.

## Pregunta para la clase

¿Por qué no conviene devolver la entidad JPA directamente? ¿"Verificar que el nombre sea único" es lógica de negocio o tecnológica?

Siguiente paso: `git checkout paso-11-middleware`
