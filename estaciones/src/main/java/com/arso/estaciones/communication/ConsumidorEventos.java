package com.arso.estaciones.communication;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.arso.estaciones.EstacionesApplication;
import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.DTO.BicicletaDTO;
import com.arso.estaciones.model.DTO.EstacionarBicicletaDTO;
import com.arso.estaciones.rest.ControladorEstaciones;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConsumidorEventos implements IConsumidorEventos{
	
	private final static String bici_Alquilada = "bicicleta-alquilada";
	private final static String bici_Alquiler_Concluido = "bicicleta-alquiler-concluido";

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(Message mensaje) throws StreamReadException, DatabindException, IOException {
		byte[] cuerpo = mensaje.getBody();

        // Deserializar el cuerpo del mensaje a un objeto de tipo Evento
        Evento evento = objectMapper.readValue(cuerpo, Evento.class);
        
        ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class);
        IServicioEstaciones servicio = context.getBean(IServicioEstaciones.class);
                
        if (evento.getTipo().equals(bici_Alquilada)) {
			servicio.retirarBicicleta(evento.getIdBicicleta());
		}
        else if (evento.getTipo().equals(bici_Alquiler_Concluido)) {
        	
        	EstacionarBicicletaDTO dto = new EstacionarBicicletaDTO(evento.getIdEstacion(), evento.getIdBicicleta());
        	servicio.estacionarBicicleta(dto);
        }
		
	}

}
