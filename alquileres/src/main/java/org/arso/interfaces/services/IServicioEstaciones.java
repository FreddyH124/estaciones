package org.arso.interfaces.services;

import java.io.IOException;

import org.arso.communication.retrofit.estaciones.model.EstacionarBicicletaDTO;
import org.arso.model.Bicicleta;
import org.arso.services.ServicioAlquileresException;

public interface IServicioEstaciones {

    void estacionarBicicleta(EstacionarBicicletaDTO estacionarBicicleta) throws ServicioAlquileresException, IOException, Exception;
}
