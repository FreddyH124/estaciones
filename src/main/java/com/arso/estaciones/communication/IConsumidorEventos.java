package com.arso.estaciones.communication;

import org.springframework.amqp.core.Message;

public interface IConsumidorEventos {
	
	public void handleEvent(Message evento);

}
