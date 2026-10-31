package com.workshop.torres.error;

import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.workshop.torres.dto.ErrorResponse;

/**
 * Manejo centralizado de errores: convierte excepciones en respuestas JSON
 * con el codigo HTTP correcto. Los controllers ya no deciden el 404 a mano.
 */
@RestControllerAdvice
public class ManejadorDeErrores {

    // 404: el recurso no existe
    @ExceptionHandler(TorreNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> noEncontrada(TorreNoEncontradaException ex, HttpServletRequest request) {
        return respuesta(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), null, request);
    }

    // 409: conflicto con el estado actual (regla de negocio: nombre unico)
    @ExceptionHandler(TorreDuplicadaException.class)
    public ResponseEntity<ErrorResponse> duplicada(TorreDuplicadaException ex, HttpServletRequest request) {
        return respuesta(HttpStatus.CONFLICT, "CONFLICT", ex.getMessage(), null, request);
    }

    // 400: algun campo no cumple las reglas de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalida(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError primerError = ex.getBindingResult().getFieldErrors().get(0);
        return respuesta(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
                primerError.getDefaultMessage(), primerError.getField(), request);
    }

    // 400: el body no es un JSON valido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> jsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return respuesta(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "El cuerpo de la peticion no es un JSON valido", null, request);
    }

    private ResponseEntity<ErrorResponse> respuesta(HttpStatus status, String error, String mensaje,
                                                    String campo, HttpServletRequest request) {
        ErrorResponse cuerpo = new ErrorResponse(error, mensaje, campo, request.getRequestURI(), Instant.now().toString());
        return ResponseEntity.status(status).body(cuerpo);
    }
}
