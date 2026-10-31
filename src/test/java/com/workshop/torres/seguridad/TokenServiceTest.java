package com.workshop.torres.seguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * Prueba unitaria del JWT: genera un token con la configuracion real
 * y lo decodifica para revisar sus claims. No levanta Spring.
 */
class TokenServiceTest {

    private final JwtConfig config = new JwtConfig();
    private final SecretKey clave = new SecretKeySpec(
            "clave-de-prueba-de-al-menos-32-caracteres!".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    private final TokenService tokenService = new TokenService(config.jwtEncoder(clave), 15);
    private final JwtDecoder decoder = config.jwtDecoder(clave);

    @Test
    void elAdminRecibeScopesDeLecturaYEscritura() {
        Jwt jwt = decoder.decode(tokenService.generar("admin", List.of("ROLE_ADMIN")));

        assertEquals("admin", jwt.getSubject());
        assertEquals("torres:read torres:write", jwt.getClaimAsString("scope"));
    }

    @Test
    void unUsuarioNormalSoloRecibeLectura() {
        Jwt jwt = decoder.decode(tokenService.generar("ana", List.of("ROLE_USER")));

        assertEquals("torres:read", jwt.getClaimAsString("scope"));
        assertEquals("workshop-torres", jwt.getClaimAsString("iss"));
    }
}
