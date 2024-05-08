package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Bicicleta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IRepositorioBicicletas extends PagingAndSortingRepository<Bicicleta, String> {
    @Query("{'estacionActual.id': ?0}")
    Page<Bicicleta> findByEstacionActualId(String idEstacion, Pageable pageable);

    @Query("{'estacionActual.id': ?0, 'disponible': true}")
    Page<Bicicleta> findByDisponibleAndEstacionActualId(String idEstacion, Pageable pageable);
}
