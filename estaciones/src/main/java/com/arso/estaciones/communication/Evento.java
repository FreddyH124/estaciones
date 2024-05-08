package com.arso.estaciones.communication;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evento implements Serializable {
	
	@JsonProperty
	private String tipo;
	@JsonProperty
	private String idBicicleta;
	@JsonProperty
	private String idEstacion;
	@JsonProperty
	private Date tiempo;
	
	public Evento(String tipo, Date tiempo, String idBicicleta, String idEstacion) {
		this.tiempo = tiempo;
		this.tipo = tipo;
		this.idBicicleta = idBicicleta;
		this.idEstacion = idEstacion;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
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

	public Date getTiempo() {
		return tiempo;
	}

	public void setTiempo(Date tiempo) {
		this.tiempo = tiempo;
	}
	
}
