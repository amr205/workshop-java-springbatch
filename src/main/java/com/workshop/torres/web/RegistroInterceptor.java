package com.workshop.torres.web;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Middleware: intercepta cada request ANTES de llegar al controller
 * y DESPUES de responder. Ningun controller tiene que repetir este codigo.
 */
@Component
public class RegistroInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RegistroInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        request.setAttribute("inicio", System.currentTimeMillis());
        request.setAttribute("requestId", requestId);
        // Id de correlacion: viaja de regreso al cliente para poder rastrear la peticion
        response.setHeader("X-Request-Id", requestId);
        log.info("[{}] --> {} {}", requestId, request.getMethod(), request.getRequestURI());
        return true;   // true = la peticion sigue hacia el controller; false = se detiene aqui
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long inicio = (Long) request.getAttribute("inicio");
        long duracion = System.currentTimeMillis() - inicio;
        log.info("[{}] <-- {} {} en {} ms", request.getAttribute("requestId"),
                response.getStatus(), request.getRequestURI(), duracion);
    }
}
