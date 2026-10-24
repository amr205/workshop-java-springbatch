# Paso 8: Pruebas unitarias con JUnit 5 y Mockito

Se agregan pruebas: unitarias con Mockito para el servicio y una de integración que levanta todo Spring.

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
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito | ← estás aquí

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

- **JUnit 5**: el framework de pruebas. `@Test` marca una prueba; `assertEquals`, `assertThrows`, etc. verifican resultados.
- **Mockito**: crea **mocks**, objetos simulados que reemplazan dependencias reales. `when(...).thenReturn(...)` define qué responden y `verify(...)` comprueba que se llamaron.
- **Patrón AAA**: **A**rrange (preparar datos y mocks), **A**ct (llamar al método), **A**ssert (verificar).

**Por qué un mock del repositorio y no la base de datos real:** la prueba queda **aislada** (prueba solo la lógica del servicio), es **rápida** y es **repetible**. Son las propiedades F.I.R.S.T.: Fast, Isolated, Repeatable, Self-validating, Timely.

La inyección por constructor (paso 3) es lo que lo hace fácil: `@InjectMocks` crea el `TorreService` pasándole los mocks por su constructor.

## Qué cambió

| Archivo | Tipo | Qué prueba |
| --- | --- | --- |
| `TorreServiceTest.java` | Unitaria (sin Spring) | 4 pruebas del servicio con `TorreRepository` y `Random` simulados |
| `TorresApplicationTests.java` | Integración (`@SpringBootTest`) | Que el contexto arranca, los beans se conectan y H2 tiene las torres |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-8-pruebas
```

### 2. Correr las pruebas

```bash
mvn test
```

Debe terminar con `Tests run: 5, Failures: 0, Errors: 0` y `BUILD SUCCESS`.

- Las 4 de `TorreServiceTest` corren en milisegundos: no arrancan Spring.
- `TorresApplicationTests` tarda más porque levanta todo el contexto con la base de datos.

### 3. Prueba esto

En `aleatoriaUsaElIndiceQueDevuelveRandom`, cambia `thenReturn(1)` por `thenReturn(0)` y corre `mvn test`. La prueba falla porque ahora sale "Arquero": con el mock, el "azar" es predecible.

## Resumen del workshop

| Paso | Concepto | Anotaciones clave |
| --- | --- | --- |
| 1 | Spring Boot | `@SpringBootApplication` |
| 2 | IoC y beans | `@Service`, `ApplicationContext` |
| 3 | Inyección de dependencias | Constructor, `@RestController`, `@GetMapping` |
| 4 | Capas y `@Bean` | `@Repository`, `@Configuration`, `@Bean` |
| 5 | Scopes | `@Scope("prototype")`, `@RequestScope` |
| 6 | Configuración | `application.properties`, `@Value` |
| 7 | Spring Data JPA | `@Entity`, `@Id`, `JpaRepository` |
| 8 | Pruebas | `@Test`, `@Mock`, `@InjectMocks`, `@SpringBootTest` |

## Pregunta para la clase

En un test de `TorreService`, ¿por qué usar un mock del repositorio en lugar de la base de datos real?
