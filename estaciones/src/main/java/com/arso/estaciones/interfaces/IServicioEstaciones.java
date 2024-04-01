package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Coordenada;
import com.arso.estaciones.model.DTO.BicicletaDTO;
import com.arso.estaciones.model.DTO.EstacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioEstaciones {
    //Servicios a gestor
    String altaEstación(String nombre, int puestos,
                           String direccion , double lat, double lng);

    String altaBicicleta(String modelo, String idEstacion);

    void bajaBicicleta(String idBicicleta, String motivo);

    Page<BicicletaDTO> getAllBiciletas(String idEstacion, Pageable pageable);


    //Servicios a todos los usuarios
    Page<EstacionDTO> getAllEstaciones(Pageable pageable);

    EstacionDTO getEstacion(String idEstacion);

    Page<BicicletaDTO> getBicicletasDisponibles(String idEstacion, Pageable pageable);

    void estacionarBicicleta(String idEstacion, String idBicicleta);
}
