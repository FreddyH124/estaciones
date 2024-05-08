package org.arso.communication;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestPublicador {

	public static void main(String[] args) throws Exception {
		
		PublicadorEventos sender = new PublicadorEventos();
		
		Evento event = new Evento("bicicleta-alquiler-concluido", new Date(), "661981d746b9b4732c1b2e49", "estaccionne");  
		
		sender.publicarEvento(event);

	}

}
