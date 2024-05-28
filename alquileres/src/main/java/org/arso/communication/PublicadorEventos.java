package org.arso.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublicadorEventos /*implements IPublicadorEventos*/ {

	//@Override
	public void publicarEvento(Evento evento) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		String host = System.getenv("RABBITMQ_HOST");
		int port = Integer.parseInt(System.getenv("RABBITMQ_PORT"));
		String username = System.getenv("RABBITMQ_USERNAME");
		String password = System.getenv("RABBITMQ_PASSWORD");

		System.out.println("Host: " + host);
		System.out.println("Port: " + port);
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);

		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		//factory.setUri("amqps://hazguuiy:sxBSsDOJonJWPEdeSN5IlJ2Ck0cl_WUK@stingray.rmq.cloudamqp.com/hazguuiy");

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		String routingKey = "arso";

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(evento);

		System.out.println(json);

		channel.basicPublish("amq.topic", routingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), json.getBytes());
		channel.close();
		connection.close();

	}

}
