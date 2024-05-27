package com.arso.estaciones.communication;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicadorEventos implements IPublicadorEventos{
	

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(Object evento) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, 
				RabbitMQConfig.ROUTING_KEY2,
				evento);
		
	}

}
