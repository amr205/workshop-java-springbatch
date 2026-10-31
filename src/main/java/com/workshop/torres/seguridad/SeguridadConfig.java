package com.workshop.torres.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain filtroDeSeguridad(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // AUTORIZACION por SCOPES que vienen dentro del JWT (claim "scope")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/torres/**").hasAuthority("SCOPE_torres:read")
                .requestMatchers("/api/v1/torres/**").hasAuthority("SCOPE_torres:write")
                .anyRequest().permitAll())
            // AUTENTICACION con Bearer token: cada request trae "Authorization: Bearer <jwt>"
            // y Spring verifica la firma y la expiracion con el JwtDecoder
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public UserDetailsService usuarios(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("ana").password(passwordEncoder.encode("ana123")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("admin123")).roles("ADMIN").build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
