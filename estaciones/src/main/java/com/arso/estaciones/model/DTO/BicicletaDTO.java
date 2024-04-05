package com.arso.estaciones.model.DTO;

import com.arso.estaciones.interfaces.IIdentificable;

public class BicicletaDTO implements IIdentificable {
    private String id;
    private boolean disponible;
    private String modelo;

    public BicicletaDTO(String id, boolean disponible, String modelo) {
        this.id = id;
        this.disponible = disponible;
        this.modelo = modelo;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
