package org.arso.repository.jpa;

import org.arso.interfaces.IIdentificable;
import org.arso.interfaces.repository.IRepositorioString;
import org.arso.repository.EntidadNoEncontrada;
import org.arso.repository.RepositorioException;
import org.arso.utils.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioJPA<T extends IIdentificable, K extends IIdentificable> implements IRepositorioString<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String add(T entity) throws RepositorioException {
        entityManager = EntityManagerHelper.getEntityManager();
        K persistableEntity = convertToEntidad(entity);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(persistableEntity);
            entityManager.getTransaction().commit();
            return persistableEntity.getId();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RepositorioException("Error persisting entity", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
        entityManager = EntityManagerHelper.getEntityManager();
        K persistableEntity = convertToEntidad(entity);
        try {
            entityManager.getTransaction().begin();

            K existingEntity = getByIdUnclosedEM(entity.getId());
            entityManager.merge(persistableEntity);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RepositorioException("Error updating entity", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
        entityManager = EntityManagerHelper.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            K existingEntity = getByIdUnclosedEM(entity.getId());
            entityManager.remove(existingEntity);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RepositorioException("Error deleting entity", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private K getByIdUnclosedEM(String id) throws EntidadNoEncontrada{
        K entity = entityManager.find(getClase(), id);
        if (entity == null) {
            throw new EntidadNoEncontrada("La entidad no existe en la base de datos.");
        }
        return entity;
    }

    @Override
    public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
        entityManager = EntityManagerHelper.getEntityManager();
        //try {
        K entity = getByIdUnclosedEM(id);
        EntityManagerHelper.closeEntityManager();
        return convertToModel(entity);
        //} catch (Exception e) {
        //throw new RepositorioException("Error getting entity by ID", e);
        //} finally {
       // }
    }

    @Override
    public List<T> getAll() throws RepositorioException, EntidadNoEncontrada {
        entityManager = EntityManagerHelper.getEntityManager();
        try {
            List<K> entityList = entityManager.createQuery("SELECT e FROM " + getClase().getSimpleName() + " e", getClase())
                    .getResultList();

            List<T> list = new ArrayList<>();

            for(K entity : entityList){
                list.add(convertToModel(entity));
            }

            return list;
        } catch (Exception e) {
            throw new RepositorioException("Error getting all entities", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public List<String> getIds() throws RepositorioException {
        entityManager = EntityManagerHelper.getEntityManager();
        try {
            return entityManager.createQuery("SELECT e.id FROM " + getClase().getSimpleName() + " e", String.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RepositorioException("Error getting entity IDs", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public abstract Class<K> getClase();
    public abstract K convertToEntidad(T entity);
    public abstract T convertToModel(K entity);
}
