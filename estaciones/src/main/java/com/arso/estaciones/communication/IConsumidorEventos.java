package com.arso.estaciones.communication;

import java.io.IOException;

import com.arso.estaciones.repository.EntidadNoEncontrada;
import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public interface IConsumidorEventos {
	
	public void handleEvent(Message evento) throws StreamReadException, DatabindException, IOException, EntidadNoEncontrada;

}
