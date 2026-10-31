package com.workshop.torres.seguridad;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final long minutosDeVida;

    public TokenService(JwtEncoder jwtEncoder, @Value("${jwt.expiracion-minutos}") long minutosDeVida) {
        this.jwtEncoder = jwtEncoder;
        this.minutosDeVida = minutosDeVida;
    }

    public String generar(String usuario, Collection<String> roles) {
        Instant ahora = Instant.now();
        // Los ROLES del usuario se traducen a SCOPES (permisos especificos)
        String scope = roles.contains("ROLE_ADMIN") ? "torres:read torres:write" : "torres:read";

        // PAYLOAD: los claims
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("workshop-torres")                                // registered: iss
                .subject(usuario)                                         // registered: sub
                .issuedAt(ahora)                                          // registered: iat
                .expiresAt(ahora.plus(minutosDeVida, ChronoUnit.MINUTES)) // registered: exp
                .claim("scope", scope)                                    // permisos
                .claim("roles", List.copyOf(roles))                       // private claim
                .build();

        // HEADER: algoritmo de firma
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        // HEADER.PAYLOAD.SIGNATURE
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
