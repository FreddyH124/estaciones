package com.arso.estaciones.interfaces;

import com.arso.estaciones.auth.AuthResponse;
import com.arso.estaciones.model.DTO.LoginDTO;
import com.arso.estaciones.model.DTO.RegistroDTO;

public interface IServicioAuth {
    AuthResponse register(RegistroDTO dto);

    AuthResponse login(LoginDTO dto);
}
