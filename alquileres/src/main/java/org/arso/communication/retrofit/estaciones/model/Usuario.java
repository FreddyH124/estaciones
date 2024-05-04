package org.arso.communication.retrofit.estaciones.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Usuario  {
    private String id;
    private String nombre;   
    private String clave;
    private Rol rol;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}


    
}
