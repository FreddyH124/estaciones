package com.arso.estaciones.config;

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
public class ExpiredJwtExceptionHandler {
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespuestaError handleExpiredJwtException(ExpiredJwtException ex) {
        return new RespuestaError("Unauthorized", ex.getMessage());
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
