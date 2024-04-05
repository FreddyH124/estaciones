package com.arso.estaciones.model.DTO;

import com.arso.estaciones.interfaces.IIdentificable;

public class EstacionDTO implements IIdentificable {
    private String id;
    private String nombre;
    private int puestos;

    public EstacionDTO(String id, String nombre, int puestos) {
        this.id = id;
        this.nombre = nombre;
        this.puestos = puestos;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
    }
}
