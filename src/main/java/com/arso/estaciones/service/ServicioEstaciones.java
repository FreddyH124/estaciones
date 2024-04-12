package com.arso.estaciones.service;

import com.arso.estaciones.EstacionesApplication;
import com.arso.estaciones.communication.Evento;
import com.arso.estaciones.communication.PublicadorEventos;
import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.Bicicleta;
import com.arso.estaciones.model.Coordenada;
import com.arso.estaciones.model.DTO.*;
import com.arso.estaciones.model.Estacion;
import com.arso.estaciones.repository.RepositorioBicicletas;
import com.arso.estaciones.repository.RepositorioEstaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String altaEstacion(AltaEstacionDTO dto) {
        Coordenada coordenada = new Coordenada(dto.getLat(), dto.getLng());
        Estacion estacion = new Estacion(dto.getNombre(), dto.getPuestos(), dto.getDireccion(),coordenada);
        repositorioEstaciones.save(estacion);
        return estacion.getId();
    }

    @Override
    public String altaBicicleta(AltaBicicletaDTO dto) {
        Optional<Estacion> estacionOptional = repositorioEstaciones.findById(dto.getIdEstacion());
        if(estacionOptional.isPresent()){
            Estacion estacion = estacionOptional.get();
            if(estacion.hayHueco()){
                Bicicleta bicicleta = new Bicicleta(dto.getModelo(), estacion);
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

        if(idBicicleta == null || idBicicleta.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(idBicicleta);

        if(bicicletaOptional.isPresent()){
            Bicicleta bicicleta = bicicletaOptional.get();
            bicicleta.darDeBaja(motivo);
            repositorioBicicletas.save(bicicleta);
        }
        
        //Creamos el evento
        Evento evento = new Evento("bicicleta-desactivada", LocalDateTime.now(), idBicicleta);
        ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class);
        PublicadorEventos publicador = context.getBean(PublicadorEventos.class);
        publicador.sendMessage(evento);
    }

    @Override
    public Page<BicicletaDTO> getAllBiciletas(String idEstacion, Pageable pageable) {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        return repositorioBicicletas.findByEstacionActualId(idEstacion, pageable).map(bicicleta -> {
            BicicletaDTO dto =DTOHelper.fromEntity(bicicleta);
            return dto;
        });
    }

    @Override
    public Page<EstacionDTO> getAllEstaciones(Pageable pageable) {
        return repositorioEstaciones.findAll(pageable).map(estacion -> {
            EstacionDTO dto =DTOHelper.fromEntity(estacion);
            return dto;
        });
    }

    @Override
    public EstacionDTO getEstacion(String idEstacion) {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        Optional<Estacion> estacionOptional = repositorioEstaciones.findById(idEstacion);
        if(estacionOptional.isPresent()){
            Estacion estacion = estacionOptional.get();
            return DTOHelper.fromEntity(estacion);
        }
        return null;
    }

    @Override
    public Page<BicicletaDTO> getBicicletasDisponibles(String idEstacion, Pageable pageable) {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        return repositorioBicicletas.findByDisponibleAndEstacionActualId(idEstacion, pageable).map(bicicleta -> {
            BicicletaDTO dto =DTOHelper.fromEntity(bicicleta);
            return dto;
        });
    }

    @Override
    public void estacionarBicicleta(EstacionarBicicletaDTO dto) {
        Optional<Estacion> estacionOptional = repositorioEstaciones.findById(dto.getIdEstacion());
        if(estacionOptional.isPresent()){
            Estacion estacion = estacionOptional.get();
            if(estacion.hayHueco()){
                Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(dto.getIdBicicleta());
                if(bicicletaOptional.isPresent()){
                    Bicicleta bicicleta = bicicletaOptional.get();
                    estacion.addBicicleta(bicicleta);
                    bicicleta.setEstacionActual(estacion);
                    bicicleta.setDisponible(true);
                    repositorioBicicletas.save(bicicleta);
                    repositorioEstaciones.save(estacion);
                }
            }
        }
    }

    @Override
    public void retirarBicicleta(String idBicicleta) {
        Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(idBicicleta);
        if(bicicletaOptional.isPresent()){
            Bicicleta bicicleta = bicicletaOptional.get();
            Estacion estacion = bicicleta.getEstacionActual();

            estacion.removeBicicleta(bicicleta);
            bicicleta.setEstacionActual(null);
            bicicleta.setDisponible(false);
            repositorioBicicletas.save(bicicleta);
            repositorioEstaciones.save(estacion);
        }

    }
}
