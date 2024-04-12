package com.arso.estaciones.communication;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorEventos implements IConsumidorEventos{

	
	@Override
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(Message evento) {
		String body = new String(evento.getBody());
		
	}

}
