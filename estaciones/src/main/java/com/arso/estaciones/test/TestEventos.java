package com.arso.estaciones.test;

import java.time.LocalDateTime;
import java.util.Date;

import com.arso.estaciones.EstacionesApplication;

import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.arso.estaciones.communication.Evento;
import com.arso.estaciones.communication.PublicadorEventos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestEventos {
	
	public static void main(String[] args) throws JsonProcessingException {
		ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class, args);
		
		PublicadorEventos sender = context.getBean(PublicadorEventos.class);
		
		Evento evento = new Evento("bicicleta-desactivada", new Date(), "661981d746b9b4732c1b2e49", "");
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(evento);
		
		sender.sendMessage(json);
	}

}
