package org.arso.model;

import org.arso.interfaces.IEntidadParseable;
import org.arso.interfaces.IIdentificable;
import org.arso.persistence.jpa.ReservaEntidad;
import org.arso.utils.DateTimeConverter;

import java.time.LocalDateTime;

public class Reserva implements IIdentificable, IEntidadParseable<ReservaEntidad> {
    private String id;
    private String idBicicleta;
    private LocalDateTime creada;
    private LocalDateTime caducidad;


    public Reserva(){

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String _id) {
        this.id = _id;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public LocalDateTime getCreada() {
        return creada;
    }

    public void setCreada(LocalDateTime creada) {
        this.creada = creada;
    }

    public LocalDateTime getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(LocalDateTime caducidad) {
        this.caducidad = caducidad;
    }

    public boolean isCaducada(){
        return LocalDateTime.now().isAfter(caducidad);
    }

    public boolean isActiva(){
        return !isCaducada();
    }

    @Override
    public ReservaEntidad toEntidad(){
        ReservaEntidad reservaEntidad = new ReservaEntidad();
        reservaEntidad.setId(this.id);
        reservaEntidad.setIdBicicleta(this.idBicicleta);
        reservaEntidad.setCreada(DateTimeConverter.convertToDate(this.creada));

        return reservaEntidad;
    }
}
