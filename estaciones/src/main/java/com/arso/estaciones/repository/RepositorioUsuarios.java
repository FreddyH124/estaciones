package com.arso.estaciones.repository;

import com.arso.estaciones.interfaces.IRepositorioUsuarios;
import com.arso.estaciones.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarios extends IRepositorioUsuarios, MongoRepository<Usuario, String> {

}
