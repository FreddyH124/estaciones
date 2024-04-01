package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Estacion;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IRepositorioEstaciones extends PagingAndSortingRepository<Estacion, String> {
}
