package com.arso.estaciones.service;

import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.Bicicleta;
import com.arso.estaciones.model.Coordenada;
import com.arso.estaciones.model.DTO.BicicletaDTO;
import com.arso.estaciones.model.DTO.EstacionDTO;
import com.arso.estaciones.model.Estacion;
import com.arso.estaciones.repository.RepositorioBicicletas;
import com.arso.estaciones.repository.RepositorioEstaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones {
    private RepositorioEstaciones repositorioEstaciones;
    private RepositorioBicicletas repositorioBicicletas;

    @Autowired
    public ServicioEstaciones(RepositorioEstaciones repositorioEstaciones, RepositorioBicicletas repositorioBicicletas){
        this.repositorioEstaciones = repositorioEstaciones;
        this.repositorioBicicletas = repositorioBicicletas;
    }

    @Override
    public String altaEstación(String nombre, int puestos, String direccion, double lat, double lng) {
        Coordenada coordenada = new Coordenada(lat, lng);
        Estacion estacion = new Estacion(nombre, puestos, direccion,coordenada);
        repositorioEstaciones.save(estacion);
        return estacion.getId();
    }

    @Override
    public String altaBicicleta(String modelo, String idEstacion) {
        Optional<Estacion> estacionOptional = repositorioEstaciones.findById(idEstacion);
        if(estacionOptional.isPresent()){
            Estacion estacion = estacionOptional.get();
            if(estacion.hayHueco()){
                Bicicleta bicicleta = new Bicicleta(modelo, estacion);
                repositorioBicicletas.save(bicicleta);

                estacion.addBicicleta(bicicleta);
                repositorioEstaciones.save(estacion);
                return bicicleta.getId();
            }
        }
        return null;
    }

    @Override
    public void bajaBicicleta(String idBicicleta, String motivo) {
        Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(idBicicleta);

        if(bicicletaOptional.isPresent()){
            Bicicleta bicicleta = bicicletaOptional.get();
            bicicleta.darDeBaja(motivo);
            repositorioBicicletas.save(bicicleta);
        }
    }

    @Override
    public Page<BicicletaDTO> getAllBiciletas(String idEstacion, Pageable pageable) {
        return null;
    }

    @Override
    public Page<EstacionDTO> getAllEstaciones(Pageable pageable) {
        return null;
    }

    @Override
    public EstacionDTO getEstacion(String idEstacion) {
        return null;
    }

    @Override
    public Page<BicicletaDTO> getBicicletasDisponibles(String idEstacion, Pageable pageable) {
        return null;
    }

    @Override
    public void estacionarBicicleta(String idEstacion, String idBicicleta) {

    }
}
