# Paso 3: Inyección de dependencias por constructor

Un controlador recibe el servicio que necesita sin crearlo: Spring se lo inyecta.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor | ← estás aquí
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean |
| 5 | `paso-5-scopes` | Scopes de los beans |
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value |
| 7 | `paso-7-jpa` | Spring Data JPA con H2 |
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

La **inyección de dependencias (DI)** es la forma en que Spring aplica IoC: un bean **recibe** sus dependencias desde afuera en lugar de crearlas. Hay tres formas:

| Forma | Ejemplo | ¿Recomendada? |
| --- | --- | --- |
| **Constructor** | `public TorreController(TorreService s) { ... }` | **Sí** |
| Setter | `@Autowired public void setServicio(TorreService s)` | Para dependencias opcionales |
| Campo | `@Autowired private TorreService servicio;` | No |

**Por qué por constructor:**

- El campo puede ser `final`: nunca cambia y **nunca es null**. Si falta la dependencia, la app falla al arrancar, no después.
- Las dependencias se ven en la firma del constructor.
- **Se prueba fácil**: en un test haces `new TorreController(servicioFalso)` sin levantar Spring.

Si la clase tiene **un solo constructor**, ni siquiera hace falta `@Autowired`: Spring lo usa automáticamente.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `TorreController.java` | Nuevo: `@RestController` que recibe `TorreService` por constructor. Endpoints `GET /torres` y `GET /torres/mas-poderosa` |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-3-inyeccion
```

Si ya lo tienes clonado: `git checkout paso-3-inyeccion`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:8080/torres
curl.exe http://localhost:8080/torres/mas-poderosa
```

`/torres` devuelve las 4 torres en JSON. Spring convierte la lista de Java a JSON automáticamente con Jackson, que viene en el starter web.

## Prueba esto

Cambia el constructor por un campo con `new TorreService()` y arranca: verás **dos** mensajes `>> Spring creo un TorreService`, porque ahora hay dos objetos (el de Spring y el tuyo). Regresa al constructor.

## Pregunta para la clase

¿Por qué se recomienda la inyección por constructor en lugar de `@Autowired` en el campo?

Siguiente paso: `git checkout paso-4-capas`
