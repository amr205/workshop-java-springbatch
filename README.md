# Paso 6: Configuración con application.properties y @Value

Valores que antes estaban escritos en el código pasan a `application.properties`.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean |
| 5 | `paso-5-scopes` | Scopes de los beans |
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value | ← estás aquí
| 7 | `paso-7-jpa` | Spring Data JPA con H2 |
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

`src/main/resources/application.properties` guarda configuración como `clave=valor`. Hay dos tipos de propiedades:

- **De Spring Boot**, como `server.port`, que cambia el puerto de Tomcat (por defecto 8080).
- **Propias**, como `juego.nombre`, que se leen con `@Value("${juego.nombre}")`.

**Ventajas:**

- Cambiar valores **sin recompilar**.
- Tener valores distintos por ambiente (desarrollo, pruebas, producción).
- Sobrescribirlos al arrancar: los argumentos de línea de comandos **ganan** sobre el archivo.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `application.properties` | `server.port=9090`, `juego.nombre` y `juego.presupuesto-inicial` |
| `JuegoController.java` | Nuevo: lee las dos propiedades con `@Value` en el constructor. Endpoint `GET /juego` |
| `TorreService.java` | Nuevo método `alcanzables(presupuesto)` |

**Ojo: desde este paso la app corre en el puerto 9090.**

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-6-configuracion
```

Si ya lo tienes clonado: `git checkout paso-6-configuracion`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:9090/juego
```

Respuesta: el nombre del juego, el presupuesto (250) y las torres que cuestan 250 o menos.

### 4. Sobrescribir propiedades al arrancar

Detén la app y arráncala con otros valores:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --juego.presupuesto-inicial=400"
```
```powershell
# Windows PowerShell: comillas alrededor de todo el argumento -D
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081 --juego.presupuesto-inicial=400"
```

Ahora responde en `http://localhost:8081/juego` y con 400 alcanzan las 4 torres. Con el JAR sería: `java -jar target/torres-spring-0.0.1-SNAPSHOT.jar --server.port=8081`.

## Pregunta para la clase

¿Qué ganas al poner configuración en `application.properties` en lugar de en el código?

Siguiente paso: `git checkout paso-7-jpa`
