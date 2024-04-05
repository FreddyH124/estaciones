package com.arso.estaciones.model;

import com.arso.estaciones.interfaces.IIdentificable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "bicicletas")
public class Bicicleta implements IIdentificable {
/*
    public enum Estado
    {
        BUEN_ESTADO,
        DAÑADA
    }
*/

    @Id
    private String id;
    //private Estado estado;
    private boolean disponible;
    private Date fechaDeAlta;
    private Date fechaDeBaja;
    private String modelo;
    @DBRef
    private Estacion estacionActual;
    private String motivoBaja;
    //private Map<String, Incidencia> incidencias;
    //private Map<String, Registro> registros;


    public Bicicleta(String modelo, Estacion estacionActual) {
        //this.estado = Estado.BUEN_ESTADO;
        this.disponible = true;
        this.fechaDeAlta = new Date();
        this.modelo = modelo;
        this.estacionActual = estacionActual;
    }

    public Bicicleta() {
        this.disponible = true;
        //this.estado = Estado.BUEN_ESTADO;
        this.fechaDeAlta = new Date();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /*public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }*/

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFechaDeAlta() {
        return fechaDeAlta;
    }

    public void setFechaDeAlta(Date fechaDeAlta) {
        this.fechaDeAlta = fechaDeAlta;
    }

    public Date getFechaDeBaja() {
        return fechaDeBaja;
    }

    public void setFechaDeBaja(Date fechaDeBaja) {
        this.fechaDeBaja = fechaDeBaja;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Estacion getEstacionActual() {
        return estacionActual;
    }

    public void setEstacionActual(Estacion estacionActual) {
        this.estacionActual = estacionActual;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public void darDeBaja(String motivo){
        setFechaDeBaja(new Date());
        setMotivoBaja(motivo);
        setDisponible(false);
        //setEstado(Estado.DAÑADA);
    }
}
