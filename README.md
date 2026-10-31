# Paso 13: JWT: tokens Bearer con scopes

El usuario inicia sesión una vez, recibe un JWT firmado y lo usa como Bearer token. Los permisos viajan dentro del token como scopes.

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
| 12 | `paso-12-seguridad` | Spring Security: autenticación vs autorización |
| 13 | `paso-13-jwt` | JWT: tokens Bearer con scopes | ← estás aquí

## Requisitos

- JDK 17 o superior: `java -version`
- Maven 3.9 o superior: `mvn -version`
- Git: `git --version`

## Concepto

**JWT (JSON Web Token)**: un token compacto y **firmado** con tres partes separadas por puntos: `HEADER.PAYLOAD.SIGNATURE`.

| Parte | Qué contiene | En este proyecto |
| --- | --- | --- |
| Header | Algoritmo de firma y tipo | `{"alg": "HS256"}` |
| Payload | Claims | `iss`, `sub`, `iat`, `exp` (registered), `scope` y `roles` |
| Signature | Garantiza que nadie modificó el token | HMAC SHA-256 con `jwt.secreto` |

**Flujo:**

```
1. POST /api/v1/auth/login  {usuario, password}   →  AuthN una sola vez
2. ←  { "token": "eyJhbGciOi...", "tipo": "Bearer" }
3. GET /api/v1/torres   Authorization: Bearer eyJhbGciOi...
4. Spring verifica firma y expiración → lee el scope → AuthZ
```

**Stateless**: el servidor no guarda sesiones; todo lo necesario viaja en el token.

**Roles → scopes**: `USER` recibe `torres:read`; `ADMIN` recibe `torres:read torres:write`. Las reglas ahora se escriben con scopes (`hasAuthority("SCOPE_torres:write")`), que son permisos más específicos que los roles.

**Relación con OAuth2**: en el mundo real, los tokens los emite un **Authorization Server** (Google, Keycloak, Auth0…) y la API solo los verifica, como **Resource Server**. Aquí, para simplificar, la misma app hace los dos papeles. Por eso usamos `spring-boot-starter-oauth2-resource-server`: OAuth2 define el **flujo** y JWT define el **formato** del token.

## Qué cambió

| Archivo | Cambio |
| --- | --- |
| `pom.xml` | Nuevo `spring-boot-starter-oauth2-resource-server` |
| `seguridad/JwtConfig.java` | Clave secreta, `JwtEncoder` (firma) y `JwtDecoder` (verifica) |
| `seguridad/TokenService.java` | Arma los claims y firma el token |
| `seguridad/AuthController.java` | `POST /api/v1/auth/login` → devuelve el JWT |
| `seguridad/SeguridadConfig.java` | Cambia HTTP Basic por `oauth2ResourceServer().jwt()` y reglas por scopes |
| `dto/LoginRequest.java`, `dto/TokenResponse.java` | DTOs del login |
| `error/CredencialesInvalidasException.java` + `ManejadorDeErrores` | Login incorrecto → 401 con JSON |
| `application.properties` | `jwt.secreto` y `jwt.expiracion-minutos=15` (expiración corta) |
| `TokenServiceTest.java` | Genera tokens y revisa sus claims |

## Desde cero

### 1. Clonar el repo e ir a este paso

```bash
git clone https://github.com/amr205/workshop-java-springbatch.git
cd workshop-java-springbatch
git checkout paso-13-jwt
```

Si ya lo tienes clonado: `git checkout paso-13-jwt`.

### 2. Arrancar la aplicación

```bash
mvn spring-boot:run
```

Déjala corriendo y abre **otra terminal** en la misma carpeta para las pruebas. Para detenerla: `Ctrl + C`.

### 3. Obtener un token

```bash
# Linux / macOS
TOKEN=$(curl -s -X POST -H "Content-Type: application/json" -d "@peticiones/login-admin.json" http://localhost:9090/api/v1/auth/login | sed -E 's/.*"token":"([^"]+)".*/\1/')
echo $TOKEN
```
```powershell
# Windows PowerShell
$token = (curl.exe -s -X POST -H "Content-Type: application/json" -d "@peticiones/login-admin.json" http://localhost:9090/api/v1/auth/login | ConvertFrom-Json).token
$token
```

Copia el token y pégalo en https://jwt.io para ver el header y el payload. **Ojo:** cualquiera puede leer el payload, porque el JWT está **firmado, no cifrado**. Nunca pongas contraseñas ni datos sensibles en él.

### 4. Usar el token

```bash
curl -i http://localhost:9090/api/v1/torres                                                       # 401: sin token
curl -i -H "Authorization: Bearer $TOKEN" http://localhost:9090/api/v1/torres                     # 200
curl -i -H "Authorization: Bearer $TOKEN" -X DELETE http://localhost:9090/api/v1/torres/1         # 204 (admin tiene torres:write)
curl -i -H "Authorization: Bearer abc.def.ghi" http://localhost:9090/api/v1/torres                # 401: firma inválida
```

En PowerShell cambia `$TOKEN` por `$token`.

Repite el paso 3 con `peticiones/login-ana.json`: con el token de ana, `GET` funciona y `DELETE` da **403**, porque su token solo trae `torres:read`.

Un login incorrecto (por ejemplo cambia la contraseña en el JSON) da **401** con el formato de error del paso 10.

### 5. Pruebas automáticas

```bash
mvn test
```

Deben pasar 9 pruebas.

## Buenas prácticas que se ven aquí

- **Expiración corta**: 15 minutos (`jwt.expiracion-minutos`).
- **El secreto fuera del código**: aquí está en `application.properties` para que el ejemplo funcione; en producción va en una variable de entorno, igual que el **Client Secret** de OAuth2.
- **HTTPS obligatorio**: sin HTTPS, cualquiera que intercepte el token puede usarlo hasta que expire.

## Pregunta para la clase

¿Qué parte del JWT impide que un usuario cambie su `scope` a `torres:write`? ¿Qué define OAuth2 y qué define JWT?
