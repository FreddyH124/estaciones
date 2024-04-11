package com.arso.estaciones.config;

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
public class IllegalArgumentExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespuestaError handleIllegalArgumentException(IllegalArgumentException ex) {
        return new RespuestaError("Bad Request", ex.getMessage());
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
