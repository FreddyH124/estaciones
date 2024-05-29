package com.arso.estaciones.service;

import com.arso.estaciones.EstacionesApplication;
import com.arso.estaciones.communication.Evento;
import com.arso.estaciones.communication.PublicadorEventos;
import com.arso.estaciones.interfaces.IRepositorioBicicletas;
import com.arso.estaciones.interfaces.IRepositorioEstaciones;
import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.Bicicleta;
import com.arso.estaciones.model.Coordenada;
import com.arso.estaciones.model.DTO.*;
import com.arso.estaciones.model.Estacion;
import com.arso.estaciones.repository.RepositorioBicicletas;
import com.arso.estaciones.repository.RepositorioEstaciones;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.arso.estaciones.repository.EntidadNoEncontrada;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones {
    private IRepositorioEstaciones repositorioEstaciones;
    private IRepositorioBicicletas repositorioBicicletas;

    @Autowired
    private PublicadorEventos publicador;

    @Autowired
    public ServicioEstaciones(IRepositorioEstaciones repositorioEstaciones, IRepositorioBicicletas repositorioBicicletas){
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
    public String altaBicicleta(AltaBicicletaDTO dto) throws EntidadNoEncontrada {
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
        }else{
            throw new EntidadNoEncontrada("La bicicleta: " + dto.getIdEstacion() + " no se encuentra en la BBDD");
        }
        return null;
    }

    @Override
    public void bajaBicicleta(String idBicicleta, String motivo) throws EntidadNoEncontrada,JsonProcessingException {

        if(idBicicleta == null || idBicicleta.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(idBicicleta);

        if(bicicletaOptional.isPresent()){
            Bicicleta bicicleta = bicicletaOptional.get();
            bicicleta.darDeBaja(motivo);
            repositorioBicicletas.save(bicicleta);

            //Creamos el evento
            Evento evento = new Evento("bicicleta-desactivada", new Date(), idBicicleta, bicicleta.getEstacionActual().getId());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(evento);
            publicador.sendMessage(json);
        }else{
            throw new EntidadNoEncontrada("La bicicleta: " + idBicicleta + " no se encuentra en la BBDD");
        }
        

    }

    @Override
    public Page<Bicicleta> getAllBiciletas(String idEstacion, Pageable pageable) {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        return repositorioBicicletas.findByEstacionActualId(idEstacion, pageable);
    }

    @Override
    public Page<Estacion> getAllEstaciones(Pageable pageable) {
        return repositorioEstaciones.findAll(pageable);
    }

    @Override
    public Estacion getEstacion(String idEstacion) throws EntidadNoEncontrada {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        Optional<Estacion> estacionOptional = repositorioEstaciones.findById(idEstacion);
        if(estacionOptional.isPresent()){
            return estacionOptional.get();
        }else{
            throw new EntidadNoEncontrada("La estacion: " + idEstacion + " no se encuentra en la BBDD");
        }
    }

    @Override
    public Page<Bicicleta> getBicicletasDisponibles(String idEstacion, Pageable pageable) {

        if(idEstacion == null || idEstacion.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        return repositorioBicicletas.findByDisponibleAndEstacionActualId(idEstacion, pageable);
    }

    @Override
    public void estacionarBicicleta(EstacionarBicicletaDTO dto) throws EntidadNoEncontrada {
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
                }else{
                    throw new EntidadNoEncontrada("La bicicleta: " + dto.getIdBicicleta() + " no se encuentra en la BBDD");
                }
            }else{
                throw new IllegalArgumentException("No hay hueco en la estacion");
            }
        }else{
            throw new EntidadNoEncontrada("La estacion: " + dto.getIdEstacion() + " no se encuentra en la BBDD");
        }
    }

    @Override
    public void retirarBicicleta(String idBicicleta) throws EntidadNoEncontrada {
        if(idBicicleta == null || idBicicleta.isEmpty()){
            throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
        }

        Optional<Bicicleta> bicicletaOptional = repositorioBicicletas.findById(idBicicleta);
        if(bicicletaOptional.isPresent()){
            Bicicleta bicicleta = bicicletaOptional.get();
            Estacion estacion = bicicleta.getEstacionActual();

            estacion.removeBicicleta(bicicleta);
            bicicleta.setEstacionActual(null);
            bicicleta.setDisponible(false);
            repositorioBicicletas.save(bicicleta);
            repositorioEstaciones.save(estacion);
        }else{
            throw new EntidadNoEncontrada("La bicicleta: " + idBicicleta + " no se encuentra en la BBDD");
        }

    }
}
