package com.arso.estaciones.repository;

import com.arso.estaciones.interfaces.IRepositorioBicicletas;
import com.arso.estaciones.model.Bicicleta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioBicicletas extends IRepositorioBicicletas, MongoRepository<Bicicleta, String> {
}
