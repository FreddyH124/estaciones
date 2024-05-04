package org.arso.interfaces.services;

import java.io.IOException;

import org.arso.model.Usuario;
import org.arso.repository.NotAllowedException;
import org.arso.repository.RepositorioException;
import org.arso.services.ServicioAlquileresException;

public interface IServicioAlquileres {
	Usuario getUsuario(String idUsuario) throws RepositorioException;
    void reservarBicicleta(String idUsuario, String idBicicleta) throws IllegalStateException, RepositorioException;
    void confirmarReserva(String idUsuario) throws IllegalStateException, RepositorioException;
    void alquilarBicicleta(String idUsuario, String idBicicleta) throws IllegalStateException, RepositorioException, Exception;

    Usuario historialUsuario(String idUsuario) throws RepositorioException;

    void dejarBicicleta(String idUsuario, String idEstacion) throws IllegalStateException, ServicioAlquileresException, IOException, RepositorioException, Exception;
    void liberarBloqueo(String idUsuario) throws RepositorioException;
}
