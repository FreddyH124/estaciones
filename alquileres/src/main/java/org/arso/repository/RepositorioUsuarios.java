package org.arso.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.arso.model.Usuario;
import org.arso.persistence.jpa.UsuarioEntidad;
import org.arso.repository.jpa.RepositorioJPA;
import org.arso.utils.EntityManagerHelper;

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
    
    public List<Usuario> buscarUsuariosPorBicicletaId(String bicicletaId) throws RepositorioException {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT u FROM UsuarioEntidad u JOIN u.reservas r WHERE r.idBicicleta = :bicicletaId";
            List<UsuarioEntidad> usuariosEntidad = em.createQuery(jpql, UsuarioEntidad.class)
                                                      .setParameter("bicicletaId", bicicletaId)
                                                      .getResultList();

            List<Usuario> usuarios = new ArrayList<>();
            for (UsuarioEntidad usuarioEntidad : usuariosEntidad) {
                usuarios.add(usuarioEntidad.toModelo());
            }

            return usuarios;
        } catch (Exception e) {
            throw new RepositorioException("Error al buscar usuarios por ID de bicicleta", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}
