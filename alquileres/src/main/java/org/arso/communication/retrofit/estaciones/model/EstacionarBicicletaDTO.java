package org.arso.communication.retrofit.estaciones.model;

public class EstacionarBicicletaDTO {
    private String idEstacion;
    private String idBicicleta;
    
    public EstacionarBicicletaDTO(String idEstacion, String idBicicleta) {
    	this.setIdBicicleta(idBicicleta);
    	this.setIdEstacion(idEstacion);
    }

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}
    
    
    
    
}
