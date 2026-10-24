# Paso 2: Contenedor IoC y beans

Ahora el contenedor de Spring crea un objeto por nosotros y lo hacemos visible en la consola.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans | ← estás aquí
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
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

**Inversión de control (IoC)**: en Java "normal" tú creas los objetos con `new`. En Spring, el **contenedor IoC** (`ApplicationContext`) los crea, los configura, los conecta y los destruye. Tu código solo declara qué necesita.

Un **bean** es un objeto Java normal **creado y gestionado por el contenedor**. Para que Spring cree un bean de una clase, se anota con `@Component` o una de sus variantes (`@Service`, `@Repository`, `@Controller`).

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `Torre.java` | Nuevo: una torre (nombre, daño, costo) |
| `TorreService.java` | Nuevo: `@Service` con 4 torres. Su constructor imprime un mensaje |
| `TorresApplication.java` | Guarda el `ApplicationContext` que devuelve `run(...)`, lista los beans de nuestro paquete y le pide el `TorreService` al contenedor |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-2-beans
```

Si ya lo tienes clonado: `git checkout paso-2-beans`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Qué observar en la consola

```
>> Spring creo un TorreService
...
== Beans creados por Spring en nuestro paquete ==
 - torresApplication -> TorresApplication$$SpringCGLIB$$0
 - holaController -> HolaController
 - torreService -> TorreService
Torre mas poderosa: Torre[nombre=Laser, danio=50, costo=400]
Es la misma instancia? true
```

- Nunca escribimos `new TorreService()`, pero el mensaje del constructor aparece: **lo creó Spring** al arrancar.
- Cada bean tiene un nombre: el de la clase con la primera letra en minúscula.
- Pedimos el bean dos veces y es **el mismo objeto**: por defecto los beans son *singleton* (lo vemos a fondo en el paso 5).

## Pregunta para la clase

Explica IoC con tus palabras: ¿quién crea los objetos en Java normal y quién en Spring?

Siguiente paso: `git checkout paso-3-inyeccion`
