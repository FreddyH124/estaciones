package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Bicicleta;
import com.arso.estaciones.model.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.arso.estaciones.model.Estacion;
import com.arso.estaciones.repository.EntidadNoEncontrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioEstaciones {
    //Servicios a gestor
    String altaEstacion(AltaEstacionDTO dto);

    String altaBicicleta(AltaBicicletaDTO dto) throws EntidadNoEncontrada;

    void bajaBicicleta(String idBicicleta, String motivo) throws EntidadNoEncontrada, JsonProcessingException;

    Page<Bicicleta> getAllBiciletas(String idEstacion, Pageable pageable);


    //Servicios a todos los usuarios
    Page<Estacion> getAllEstaciones(Pageable pageable);

    Estacion getEstacion(String idEstacion) throws EntidadNoEncontrada;

    Page<Bicicleta> getBicicletasDisponibles(String idEstacion, Pageable pageable);

    void estacionarBicicleta(EstacionarBicicletaDTO dto) throws EntidadNoEncontrada;

    void retirarBicicleta(String idBicicleta) throws EntidadNoEncontrada;
}
