package com.arso.estaciones.repository;

import com.arso.estaciones.interfaces.IRepositorioEstaciones;
import com.arso.estaciones.model.Estacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEstaciones extends IRepositorioEstaciones, MongoRepository<Estacion, String> {
}
