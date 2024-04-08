package com.arso.estaciones.test;

import com.arso.estaciones.EstacionesApplication;
import com.arso.estaciones.model.DTO.RegistroDTO;
import com.arso.estaciones.model.Rol;
import com.arso.estaciones.service.ServicioAuth;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestJWT {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class, args);
        ServicioAuth servicioAuth = context.getBean(ServicioAuth.class);

        RegistroDTO dto = new RegistroDTO("Juan", "admin", Rol.GESTOR);
        String token = String.valueOf(servicioAuth.register(dto));
        System.out.println(token);
    }
}
