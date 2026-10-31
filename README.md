# Paso 12: Spring Security: autenticación vs autorización

La API exige usuario y contraseña, y no todos los usuarios pueden hacer lo mismo.

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
| 10 | `paso-10-dtos-y-errores` | DTOs, validación y errores con estructura |
| 11 | `paso-11-middleware` | Middleware: un interceptor para todas las peticiones |
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización | ← estás aquí
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes |

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

| | Autenticación (AuthN) | Autorización (AuthZ) |
| --- | --- | --- |
| Pregunta | **¿Quién eres?** | **¿Qué puedes hacer?** |
| Aquí | Usuario y contraseña con HTTP Basic | Roles `USER` y `ADMIN` |
| Si falla | **401 Unauthorized** | **403 Forbidden** |

**Spring Security** funciona como una cadena de filtros (otro middleware) que revisa cada petición **antes** de que llegue al controller. Se configura con un bean `SecurityFilterChain`.

Reglas de este paso:

| Petición | ¿Quién puede? |
| --- | --- |
| `GET /api/v1/torres/**` | `USER` y `ADMIN` |
| `POST`, `PUT`, `PATCH`, `DELETE` en `/api/v1/torres/**` | Solo `ADMIN` |
| `/hola`, `/juego`, `/scopes` | Todos, sin login |

| Usuario | Contraseña | Rol |
| --- | --- | --- |
| `ana` | `ana123` | USER |
| `admin` | `admin123` | ADMIN |

Las contraseñas se guardan con **hash** (bcrypt), nunca en texto plano.

**¿Por qué `csrf.disable()`?** CSRF protege formularios con cookie de sesión. Esta API es **stateless** (no guarda sesión), así que no aplica.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `pom.xml` | Nuevo `spring-boot-starter-security` |
| `seguridad/SeguridadConfig.java` | `SecurityFilterChain` con las reglas, usuarios en memoria y `PasswordEncoder` |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-12-seguridad
```

Si ya lo tienes clonado: `git checkout paso-12-seguridad`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

Déjala corriendo y abre **otra terminal** en la misma carpeta para las pruebas. Para detenerla: `Ctrl + C`.

### 3. Probar

Los comandos usan `curl`. En **PowerShell escribe `curl.exe`** (con `.exe`); el resto del comando es igual. `-i` muestra el código de estado y los headers de la respuesta. Los cuerpos JSON están en la carpeta `peticiones/` y se envían con `-d "@archivo"`, así no hay problemas con las comillas en Windows.

```bash
curl -i http://localhost:9090/api/v1/torres                                       # 401: no dijiste quién eres
curl -i -u ana:ana123 http://localhost:9090/api/v1/torres                         # 200
curl -i -u ana:wrong http://localhost:9090/api/v1/torres                          # 401: contraseña incorrecta
curl -i -u ana:ana123 -X DELETE http://localhost:9090/api/v1/torres/1             # 403: ana es USER
curl -i -u admin:admin123 -X DELETE http://localhost:9090/api/v1/torres/1         # 204
curl -i http://localhost:9090/hola                # 200: ruta pública
```

`-u usuario:contraseña` envía el header `Authorization: Basic ...`. Es solo Base64, **no cifrado**: por eso HTTP Basic solo es aceptable sobre HTTPS.

Si abres `http://localhost:9090/api/v1/torres` en el navegador, aparece una ventana pidiendo usuario y contraseña: es el 401 de HTTP Basic.

## Lo que no es ideal (se mejora en el paso 13)

El cliente envía la **contraseña en cada petición**. Con JWT la envía una sola vez, recibe un token temporal y usa el token.

## Pregunta para la clase

¿Cuál es la diferencia entre 401 y 403? Con `ana`, ¿qué peticiones dan cada uno?

Siguiente paso: `git checkout paso-13-jwt`
