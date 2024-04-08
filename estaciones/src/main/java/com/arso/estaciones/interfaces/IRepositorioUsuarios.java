package com.arso.estaciones.interfaces;

import com.arso.estaciones.model.Usuario;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IRepositorioUsuarios extends PagingAndSortingRepository<Usuario, String> {

}
