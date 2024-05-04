package org.arso.communication;

import java.time.LocalDateTime;

public class Evento {
	
	private String tipo;
	private String idBicicleta;
	private LocalDateTime tiempo;
	
	public Evento(String tipo, LocalDateTime tiempo, String idBicicleta) {
		this.tiempo = tiempo;
		this.tipo = tipo;
		this.idBicicleta = idBicicleta;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public Evento() {
		
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getTiempo() {
		return tiempo;
	}

	public void setTiempo(LocalDateTime tiempo) {
		this.tiempo = tiempo;
	}
	
	

}
