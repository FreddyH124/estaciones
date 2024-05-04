package org.arso.communication.retrofit;

import org.arso.communication.retrofit.estaciones.model.EstacionarBicicletaDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstacionesRestClient {
	
	@POST("estaciones/estacionar")
	Call<Void> estacionarBicicleta(@Body EstacionarBicicletaDTO estacionarBicicletaDTO);
}
