# Paso 5: Scopes de los beans

Un endpoint muestra cómo se comporta cada scope (alcance) de un bean.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean |
| 5 | `paso-5-scopes` | Scopes de los beans | ← estás aquí
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value |
| 7 | `paso-7-jpa` | Spring Data JPA con H2 |
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

El **scope** decide cuántas instancias de un bean existen:

| Scope | Instancias | Cómo se declara |
| --- | --- | --- |
| **singleton** (por defecto) | Una para toda la aplicación, compartida | Nada, o `@Scope("singleton")` |
| prototype | Una nueva cada vez que se pide | `@Scope("prototype")` |
| request | Una por petición HTTP | `@RequestScope` |
| session | Una por sesión de usuario | `@SessionScope` |

## Qué cambió

Paquete nuevo `com.workshop.torres.scopes`:

| Archivo | Scope |
| --- | --- |
| `ContadorSingleton.java` | singleton |
| `ContadorPrototype.java` | prototype |
| `ContadorPeticion.java` | request |
| `ScopeController.java` | `GET /scopes`: incrementa cada contador y devuelve los valores |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-5-scopes
```

Si ya lo tienes clonado: `git checkout paso-5-scopes`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:8080/scopes
```

Llámalo **3 veces** y compara:

| Llamada | `singleton` | `prototypeInyectado` | `prototypeNuevo` | `peticion` |
| --- | --- | --- | --- | --- |
| 1 | 1 | 1 | 1 | 2 |
| 2 | 2 | 2 | 1 | 2 |
| 3 | 3 | 3 | 1 | 2 |

- **singleton** crece: todos los usuarios comparten la misma instancia.
- **prototypeInyectado** también crece. Es la trampa: el controller es singleton y se creó una sola vez, así que el prototype se le inyectó **una sola vez**.
- **prototypeNuevo** siempre vale 1: con `ObjectProvider.getObject()` pedimos una instancia nueva en cada llamada.
- **peticion** siempre vale 2: se incrementa dos veces dentro de la misma petición (misma instancia) y en la siguiente petición empieza de cero.

## La lección: los singletons no deben guardar estado

Si dos usuarios llaman a la vez a un singleton que guarda un contador en un campo, los dos leen 5, los dos escriben 6 y se pierde un incremento: una **condición de carrera**. Por eso los servicios deben ser **sin estado**: los datos van en parámetros, variables locales o la base de datos.

## Pregunta para la clase

Un bean singleton guarda un contador en un atributo. ¿Qué pasa si dos usuarios lo llaman al mismo tiempo?

Siguiente paso: `git checkout paso-6-configuracion`
