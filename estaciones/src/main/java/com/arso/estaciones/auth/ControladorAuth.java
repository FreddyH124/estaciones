package com.arso.estaciones.auth;

import com.arso.estaciones.model.DTO.LoginDTO;
import com.arso.estaciones.model.DTO.RegistroDTO;
import com.arso.estaciones.service.ServicioAuth;
import io.jsonwebtoken.Jwts;
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
public class ControladorAuth {

    private ServicioAuth servicioAuth;

    @Autowired
    public ControladorAuth(ServicioAuth servicioAuth) {
        this.servicioAuth = servicioAuth;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(servicioAuth.login(dto));
        /*Map<String, Object> claims = verificarCredenciales(user, password);
        if (claims != null) {
            Date caducidad = Date.from(Instant.now().plusSeconds(3600));
            String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, "secreto")
                    .setExpiration(caducidad).compact();

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }*/
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@RequestBody RegistroDTO dto){
        System.out.println(dto.getNombre());
        System.out.println(dto.getClave());
        System.out.println(dto.getRol());
        return ResponseEntity.ok(servicioAuth.register(dto));
    }

    private Map<String, Object> verificarCredenciales(String username, String password) {
        // Aquí iría la lógica de verificación de credenciales
        // Por simplicidad, siempre se devuelve un usuario administrador
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("roles", "administrador");
        return claims;
    }
}
