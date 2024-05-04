package org.arso.communication.retrofit;

import java.io.IOException;

import org.arso.communication.retrofit.estaciones.model.EstacionarBicicletaDTO;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

class RetrofitTest {

	public static void main(String[] args) throws Exception {

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080")
				.addConverterFactory(JacksonConverterFactory.create()).build();

		EstacionesRestClient service = retrofit.create(EstacionesRestClient.class);
		
		EstacionarBicicletaDTO e = new EstacionarBicicletaDTO("6616cee50a78571ff80a8ff3", "660eef8e7a03036e1364df7f");

		Call<Void> call = service.estacionarBicicleta(e);

		try {
			Response<Void> response = call.execute();
			if (response.isSuccessful()) {
				System.out.println("bien");
			} else {
				System.out.println("mal");
			}
		} catch (IOException ex) {
			System.out.println("e");
		}

		System.out.println("fin. ");
	}

}
