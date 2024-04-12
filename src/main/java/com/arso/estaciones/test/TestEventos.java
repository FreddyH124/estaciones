package com.arso.estaciones.test;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.arso.estaciones.communication.Evento;
import com.arso.estaciones.communication.PublicadorEventos;

public class TestEventos {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BusEventos.class, args);
		
		PublicadorEventos sender = context.getBean(PublicadorEventos.class);
		
		Evento evento = new Evento("bicicleta-desactivada", LocalDateTime.now(), "661981d746b9b4732c1b2e49");
		
		sender.sendMessage(evento);
	}

}
