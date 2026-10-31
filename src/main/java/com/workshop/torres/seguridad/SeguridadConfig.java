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
            // API stateless: no hay formulario ni cookie de sesion, asi que CSRF no aplica
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // AUTORIZACION (AuthZ): que puede hacer cada rol
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/v1/torres/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/torres/**").hasRole("ADMIN")
                .anyRequest().permitAll())
            // AUTENTICACION (AuthN): usuario y contrasena en el header Authorization (HTTP Basic)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Usuarios de prueba en memoria. En un sistema real vendrian de una base de datos.
    @Bean
    public UserDetailsService usuarios(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("ana").password(passwordEncoder.encode("ana123")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("admin123")).roles("ADMIN").build());
    }

    // Las contrasenas nunca se guardan en texto plano: se guardan con hash (bcrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
