package org.arso.services;

import java.io.IOException;

import org.arso.communication.retrofit.EstacionesRestClient;
import org.arso.communication.retrofit.estaciones.model.EstacionarBicicletaDTO;
import org.arso.interfaces.services.IServicioEstaciones;
import org.arso.model.Bicicleta;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioEstaciones implements IServicioEstaciones {
    @Override
    public void estacionarBicicleta(EstacionarBicicletaDTO estacionarBici) throws ServicioAlquileresException, IOException{
        
    	Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080")
				.addConverterFactory(JacksonConverterFactory.create()).build();

		EstacionesRestClient service = retrofit.create(EstacionesRestClient.class);
		
		Call<Void> call = service.estacionarBicicleta(estacionarBici);

		Response<Void> response = call.execute();

    }
}
