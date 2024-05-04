package com.arso.estaciones.service;

import com.arso.estaciones.auth.AuthResponse;
import com.arso.estaciones.interfaces.IServicioAuth;
import com.arso.estaciones.model.DTO.LoginDTO;
import com.arso.estaciones.model.DTO.RegistroDTO;
import com.arso.estaciones.model.Usuario;
import com.arso.estaciones.repository.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class ServicioAuth implements IServicioAuth {
    private RepositorioUsuarios repositorioUsuarios;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public ServicioAuth(RepositorioUsuarios repositorioUsuarios, PasswordEncoder passwordEncoder,
                        JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegistroDTO dto) {
        Usuario usuario = Usuario.builder()
                            .nombre(dto.getNombre())
                            .clave(passwordEncoder.encode(dto.getClave()))
                            .rol(dto.getRol())
                            .build();

        repositorioUsuarios.save(usuario);
        String jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getNombre(),
                        dto.getClave()
                )
        );

        Usuario usuario = repositorioUsuarios.findByNombre(dto.getNombre())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(jwtToken).build();


    }
}
