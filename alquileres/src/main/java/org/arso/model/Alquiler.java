package org.arso.model;

import org.arso.interfaces.IEntidadParseable;
import org.arso.interfaces.IIdentificable;
import org.arso.persistence.jpa.AlquilerEntidad;
import org.arso.utils.DateTimeConverter;

import java.time.Duration;
import java.time.LocalDateTime;

public class Alquiler implements IIdentificable, IEntidadParseable<AlquilerEntidad> {
    private String id;
    private String idBicicleta;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Alquiler(){

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

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public boolean isActivo(){
        return fin == null;
    }

    public long getTiempoAlquiler(){
        if (fin == null) {
            return Duration.between(inicio, LocalDateTime.now()).toMinutes();
        }

        return Duration.between(inicio, fin).toMinutes();
    }

    @Override
    public AlquilerEntidad toEntidad(){
        AlquilerEntidad alquilerEntidad = new AlquilerEntidad();
        alquilerEntidad.setId(this.id);
        alquilerEntidad.setIdBicicleta(this.idBicicleta);
        alquilerEntidad.setInicio(DateTimeConverter.convertToDate(this.inicio));
        if(fin != null){
            alquilerEntidad.setFin(DateTimeConverter.convertToDate(this.fin));
        }

        return alquilerEntidad;
    }
}
