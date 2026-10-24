# Paso 1: Spring Boot: la aplicación mínima

Workshop de Spring Boot paso a paso con el tema del Tower Defense. Cada rama agrega **un concepto**. Este es el punto de partida: la aplicación Spring Boot más pequeña que responde por HTTP.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima | ← estás aquí
| 2 | `paso-2-beans` | Contenedor IoC y beans |
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

**Spring Boot** es una capa sobre Spring que permite arrancar una aplicación con casi nada de configuración. Hace cuatro cosas por ti:

1. **Starters**: una dependencia trae un conjunto completo de librerías compatibles. Aquí usamos `spring-boot-starter-webmvc` (Spring MVC, JSON y Tomcat).
2. **Autoconfiguración**: revisa qué hay en el classpath y lo configura con valores razonables. Ve Tomcat, así que levanta un servidor web.
3. **Servidor embebido**: Tomcat corre *dentro* de tu aplicación; no se instala aparte.
4. **JAR ejecutable**: `mvn package` crea un JAR que se corre con `java -jar`.

La clase principal lleva `@SpringBootApplication`, que junta tres anotaciones:

- `@Configuration`: la clase puede definir beans.
- `@EnableAutoConfiguration`: activa la autoconfiguración.
- `@ComponentScan`: busca componentes en **este paquete y sus subpaquetes**.

## Qué hay en este paso

| Archivo | Qué hace |
| --- | --- |
| `pom.xml` | Hereda de `spring-boot-starter-parent` 4.0.5 y usa el starter web |
| `TorresApplication.java` | Clase principal con `@SpringBootApplication` y `SpringApplication.run(...)` |
| `HolaController.java` | Un endpoint `GET /hola` para comprobar que Tomcat responde |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-1-spring-boot
```

Si ya lo tienes clonado: `git checkout paso-1-spring-boot`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

En la consola debe aparecer algo como `Tomcat started on port 8080`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:8080/hola
```

Respuesta: `Hola desde Spring Boot`.

## Pregunta para la clase

¿Qué tuvimos que instalar para tener un servidor web? Nada: Tomcat vino dentro del starter. ¿Qué pasaría si moviéramos `HolaController` a un paquete `com.otro`? Spring no lo encontraría (`@ComponentScan` solo busca en `com.workshop.torres` y sus subpaquetes) y `/hola` daría 404.

Siguiente paso: `git checkout paso-2-beans`
