package com.arso.estaciones.config;

import com.arso.estaciones.repository.EntidadNoEncontrada;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntidadNoEncontradaHandler {
    @ExceptionHandler(EntidadNoEncontrada.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespuestaError handleEntidadNoEncontradaException(EntidadNoEncontrada ex) {
        return new RespuestaError("Not Found", ex.getMessage());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RespuestaError{
        private String estado;
        private String mensaje;
    }
}
