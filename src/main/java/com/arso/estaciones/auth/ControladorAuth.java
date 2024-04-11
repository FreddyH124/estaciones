package com.arso.estaciones.auth;

import com.arso.estaciones.model.DTO.LoginDTO;
import com.arso.estaciones.model.DTO.RegistroDTO;
import com.arso.estaciones.service.ServicioAuth;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class ControladorAuth {

    private ServicioAuth servicioAuth;

    @Autowired
    public ControladorAuth(ServicioAuth servicioAuth) {
        this.servicioAuth = servicioAuth;
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Inicia sesión de un usuario"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(servicioAuth.login(dto));
    }

    @Operation(
            summary = "Registro de usuario",
            description = "Registra un nuevo usuario"
    )
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@RequestBody RegistroDTO dto){
        return ResponseEntity.ok(servicioAuth.register(dto));
    }
}
