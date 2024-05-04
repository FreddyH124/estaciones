package org.arso.communication;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestEventos {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory(); //Prueba
		factory.setUri("amqps://hazguuiy:sxBSsDOJonJWPEdeSN5IlJ2Ck0cl_WUK@stingray.rmq.cloudamqp.com/hazguuiy");

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();
		
		final String exchangeName = "amq.topic";
		final String queueName = "citybike-estaciones";
		final String bindingKey = "arso";

		boolean durable = true;
		boolean exclusive = false;
		boolean autodelete = false;
		Map<String, Object> properties = null;
		channel.queueDeclare(
				queueName, 
				durable, 
				exclusive, 
				autodelete, 
				properties);
		channel.queueBind(queueName, exchangeName, bindingKey);

		boolean autoAck = false;
		String etiquetaConsumidor = "alquileres-consumidor";
		
		
		channel.basicConsume(queueName, autoAck, etiquetaConsumidor, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				
				String routingKey = envelope.getRoutingKey();
				String contentType = properties.getContentType();
				long deliveryTag = envelope.getDeliveryTag();
				
				String contenido = new String(body);
				System.out.println(contenido);
				
				// Confirma el procesamiento
				channel.basicAck(deliveryTag, false);
			}
		});
		
	}

}
