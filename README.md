# Paso 4: Estereotipos, capas y @Bean

La aplicación se organiza en tres capas, cada una con su anotación, y aparece `@Bean` para registrar una clase que no es nuestra.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean | ← estás aquí
| 5 | `paso-5-scopes` | Scopes de los beans |
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value |
| 7 | `paso-7-jpa` | Spring Data JPA con H2 |
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

`@Service`, `@Repository` y `@Controller` son **especializaciones de `@Component`**: todas registran un bean igual. La diferencia es el **significado**: indican a qué capa pertenece la clase.

```
HTTP ──▶ @RestController ──▶ @Service ──▶ @Repository ──▶ datos
         TorreController      TorreService   TorreRepository
         (capa web)           (negocio)      (datos)
```

- **Controller**: recibe la petición HTTP y delega. No tiene lógica de negocio.
- **Service**: las reglas del negocio. No sabe de HTTP ni de cómo se guardan los datos.
- **Repository**: el acceso a datos. `@Repository` además traduce excepciones de base de datos a las de Spring.

**`@Bean`**: ¿y si necesitas como bean una clase que no puedes anotar, como `java.util.Random` del JDK o una clase de otra librería? Escribes un método con `@Bean` dentro de una clase `@Configuration`; lo que devuelve el método se registra como bean.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `TorreRepository.java` | Nuevo: `@Repository` con las torres en memoria |
| `TorreService.java` | Ya no tiene la lista: recibe `TorreRepository` y `Random` por constructor. Nuevo método `aleatoria()` |
| `AppConfig.java` | Nuevo: `@Configuration` con un `@Bean` que crea el `Random` |
| `TorreController.java` | Nuevo endpoint `GET /torres/aleatoria` |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-4-capas
```

Si ya lo tienes clonado: `git checkout paso-4-capas`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:8080/torres
curl.exe http://localhost:8080/torres/aleatoria
```

`/torres/aleatoria` devuelve una torre distinta casi cada vez. En la consola, la lista de beans ahora incluye `appConfig`, `random` (creado por el método `@Bean`) y `torreRepository`.

## Pregunta para la clase

¿Qué diferencia hay entre `@Component`, `@Service`, `@Repository` y `@Controller`?

Siguiente paso: `git checkout paso-5-scopes`
