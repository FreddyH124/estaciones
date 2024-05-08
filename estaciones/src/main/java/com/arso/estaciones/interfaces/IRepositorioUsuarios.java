package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Usuario;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@NoRepositoryBean
public interface IRepositorioUsuarios extends PagingAndSortingRepository<Usuario, String> {
    Optional<Usuario> findByNombre(String nombre);
}
