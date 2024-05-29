package org.arso.communication;

import java.io.IOException;
import java.util.Map;

import org.arso.repository.RepositorioException;
import org.arso.services.ServicioAlquileres;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumidorEventos {

	public void Escuchar() throws Exception {

		ServicioAlquileres servicioAlquileres = new ServicioAlquileres();

		ConnectionFactory factory = new ConnectionFactory();
		String host = System.getenv("RABBITMQ_HOST");
		int port = Integer.parseInt(System.getenv("RABBITMQ_PORT"));
		String username = System.getenv("RABBITMQ_USERNAME");
		String password = System.getenv("RABBITMQ_PASSWORD");

		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		final String exchangeName = "amq.topic";
		final String queueName = "citybike-alquileres";
		final String bindingKey = "arso2";

		boolean durable = true;
		boolean exclusive = false;
		boolean autodelete = false;
		Map<String, Object> properties = null;
		channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
		channel.queueBind(queueName, exchangeName, bindingKey);

		boolean autoAck = false;
		String etiquetaConsumidor = "alquileres-consumidor";

		ObjectMapper mapper = new ObjectMapper();

		channel.basicConsume(queueName, autoAck, etiquetaConsumidor, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
									   byte[] body) throws IOException {

				long deliveryTag = envelope.getDeliveryTag();
				String contenido = new String(body);

				try {
					Evento evento = mapper.readValue(contenido, Evento.class);

					if ("bicicleta-desactivada".equals(evento.getTipo())) {
						try {
							servicioAlquileres.eliminarReserva(evento.getIdBicicleta());
						} catch (RepositorioException e) {
							e.printStackTrace();
						}
					}

					// Confirma el procesamiento
					channel.basicAck(deliveryTag, false);
				} catch (IOException e) {
					e.printStackTrace();
					// Nack para volver a poner el mensaje en la cola
					channel.basicNack(deliveryTag, false, true);
				}
			}
		});

		System.out.println("Servicio escuchando...");
	}
}
