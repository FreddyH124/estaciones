package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Bicicleta;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IRepositorioBicicletas extends PagingAndSortingRepository<Bicicleta, String> {
}
