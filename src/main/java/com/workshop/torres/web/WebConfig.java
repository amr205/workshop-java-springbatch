package com.workshop.torres.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RegistroInterceptor registroInterceptor;

    public WebConfig(RegistroInterceptor registroInterceptor) {
        this.registroInterceptor = registroInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Solo para la API; /hola, /scopes y /juego no pasan por el interceptor
        registry.addInterceptor(registroInterceptor).addPathPatterns("/api/**");
    }
}
