package com.arso.estaciones.test;

import com.arso.estaciones.EstacionesApplication;
import com.arso.estaciones.interfaces.IServicioEstaciones;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestServicioEstaciones {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class, args);
        IServicioEstaciones servicio = context.getBean(IServicioEstaciones.class);

        /*String id = servicio.altaEstación("Estacion test", 5, "Calle de test", 33, 56);

        System.out.println("El id de la estacion guardada es: " + id);*/

        /*String idBicicleta = servicio.altaBicicleta("modelo Scott", "660a8a99c413d95cf2d24f92");
        System.out.println("El id de la estacion guardada es: " + idBicicleta);*/

        //servicio.bajaBicicleta("660ee3319c58675f44b0d337", "motivo de test");


        context.close();
    }
}
