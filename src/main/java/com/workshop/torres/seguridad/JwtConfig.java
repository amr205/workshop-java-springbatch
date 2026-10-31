package com.workshop.torres.seguridad;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

/**
 * La app firma y verifica sus propios JWT con una clave secreta (HMAC SHA-256).
 * En un sistema real los tokens los emite un Authorization Server (OAuth2)
 * y esta API solo los verifica (Resource Server).
 */
@Configuration
public class JwtConfig {

    @Bean
    public SecretKey claveJwt(@Value("${jwt.secreto}") String secreto) {
        return new SecretKeySpec(secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    // Firma (crea la SIGNATURE del token)
    @Bean
    public JwtEncoder jwtEncoder(SecretKey claveJwt) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(claveJwt));
    }

    // Verifica la firma y la expiracion de cada token que llega
    @Bean
    public JwtDecoder jwtDecoder(SecretKey claveJwt) {
        return NimbusJwtDecoder.withSecretKey(claveJwt).macAlgorithm(MacAlgorithm.HS256).build();
    }
}
