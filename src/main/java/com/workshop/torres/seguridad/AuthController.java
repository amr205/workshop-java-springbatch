package com.workshop.torres.seguridad;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.torres.dto.LoginRequest;
import com.workshop.torres.dto.TokenResponse;
import com.workshop.torres.error.CredencialesInvalidasException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserDetailsService usuarios;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final long minutosDeVida;

    public AuthController(UserDetailsService usuarios, PasswordEncoder passwordEncoder, TokenService tokenService,
                          @Value("${jwt.expiracion-minutos}") long minutosDeVida) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.minutosDeVida = minutosDeVida;
    }

    // AUTENTICACION: verifica usuario y contrasena UNA vez y entrega un JWT.
    // Despues el cliente ya no manda la contrasena: manda el token.
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest login) {
        UserDetails usuario;
        try {
            usuario = usuarios.loadUserByUsername(login.usuario());
        } catch (UsernameNotFoundException e) {
            throw new CredencialesInvalidasException();
        }
        if (!passwordEncoder.matches(login.password(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }
        List<String> roles = usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = tokenService.generar(usuario.getUsername(), roles);
        return new TokenResponse(token, "Bearer", minutosDeVida);
    }
}
