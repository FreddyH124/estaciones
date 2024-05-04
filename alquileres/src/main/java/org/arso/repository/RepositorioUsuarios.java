package org.arso.repository;

import org.arso.model.Usuario;
import org.arso.persistence.jpa.UsuarioEntidad;
import org.arso.repository.jpa.RepositorioJPA;

public class RepositorioUsuarios extends RepositorioJPA<Usuario, UsuarioEntidad> {
    @Override
    public Class<UsuarioEntidad> getClase() {
        return UsuarioEntidad.class;
    }

    @Override
    public UsuarioEntidad convertToEntidad(Usuario entity) {
        return entity.toEntidad();
    }

    @Override
    public Usuario convertToModel(UsuarioEntidad entity) {
        return entity.toModelo();
    }
}
