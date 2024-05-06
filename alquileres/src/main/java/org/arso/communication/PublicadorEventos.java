package org.arso.communication;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublicadorEventos implements IPublicadorEventos {

	@Override
	public void publicarEvento(Evento evento) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://hazguuiy:sxBSsDOJonJWPEdeSN5IlJ2Ck0cl_WUK@stingray.rmq.cloudamqp.com/hazguuiy");

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		String routingKey = "arso";

		channel.basicPublish("amq.topic", routingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), evento.getTipo().getBytes());
		channel.close();
		connection.close();

	}

}
