# Paso 7: Spring Data JPA con H2

Las torres se guardan en una base de datos H2 en memoria y el repositorio pasa a ser una interfaz de Spring Data JPA.

## Pasos del workshop

| Paso | Rama | Concepto |
| --- | --- | --- |
| 1 | `paso-1-spring-boot` | Spring Boot: la aplicación mínima |
| 2 | `paso-2-beans` | Contenedor IoC y beans |
| 3 | `paso-3-inyeccion` | Inyección de dependencias por constructor |
| 4 | `paso-4-capas` | Estereotipos, capas y @Bean |
| 5 | `paso-5-scopes` | Scopes de los beans |
| 6 | `paso-6-configuracion` | Configuración con application.properties y @Value |
| 7 | `paso-7-jpa` | Spring Data JPA con H2 | ← estás aquí
| 8 | `paso-8-pruebas` | Pruebas unitarias con JUnit 5 y Mockito |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

**Spring Data JPA** genera la capa de datos por ti. Escribes una **interfaz** que extiende `JpaRepository<Entidad, TipoDelId>` y Spring crea la implementación al arrancar:

- **CRUD gratis**: `findAll`, `findById`, `save`, `saveAll`, `deleteById`, `count`…
- **Consultas por nombre de método**: `findByNombre` se convierte en `SELECT ... WHERE nombre = ?`.
- Paginación, ordenamiento y `@Query` para consultas personalizadas.

Una **entidad** es una clase marcada con `@Entity` que se guarda en una tabla, con su llave primaria marcada con `@Id`. Por debajo, **Hibernate** es la implementación de JPA. **H2** es una base de datos en memoria: se crea al arrancar y se borra al detener la app, ideal para aprender.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `pom.xml` | Nuevos `spring-boot-starter-data-jpa` y `h2` (scope `runtime`) |
| `Torre.java` | Ahora es `@Entity` con `@Id` y `@GeneratedValue` |
| `TorreRepository.java` | De clase en memoria a **interfaz** `extends JpaRepository<Torre, Long>` con 3 consultas derivadas |
| `TorreService.java` | Usa las consultas del repositorio. Nuevo `buscarPorNombre` |
| `AppConfig.java` | Un `@Bean CommandLineRunner` que guarda las 4 torres al arrancar |
| `TorreController.java` | Nuevo `GET /torres/buscar?nombre=...` |
| `application.properties` | `spring.jpa.show-sql=true` para ver el SQL generado |

Nota: el controller y casi todo el servicio no cambiaron. Gracias a las capas, cambiar la forma de guardar datos solo tocó la capa de datos.

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-7-jpa
```

Si ya lo tienes clonado: `git checkout paso-7-jpa`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

La primera vez Maven descarga Spring Boot y sus dependencias, así que tarda unos minutos. Para detener la aplicación: `Ctrl + C`.

### 3. Probar

Abre las URLs en el navegador o usa `curl` (en PowerShell escribe `curl.exe`):

```bash
curl.exe http://localhost:9090/torres
curl.exe http://localhost:9090/torres/mas-poderosa
curl.exe http://localhost:9090/torres/buscar?nombre=Mago
curl.exe http://localhost:9090/juego
```

Ahora las torres tienen un `id` generado por la base de datos. En la consola aparece el SQL de cada consulta, por ejemplo `select ... from torre t1_0 order by t1_0.danio desc fetch first ? rows only`. Nadie escribió ese SQL: salió del nombre `findFirstByOrderByDanioDesc`.

## Pregunta para la clase

¿Qué te da extender `JpaRepository`? ¿Qué SQL crees que genera `findByCostoLessThanEqual`?

Siguiente paso: `git checkout paso-8-pruebas`
