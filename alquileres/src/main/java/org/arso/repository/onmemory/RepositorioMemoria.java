package org.arso.repository.onmemory;

import org.arso.interfaces.IIdentificable;
import org.arso.interfaces.repository.IRepositorioString;
import org.arso.repository.EntidadNoEncontrada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioMemoria<T extends IIdentificable> implements IRepositorioString<T> {
	
	private HashMap<String, T> entidades = new HashMap<>();
	
	private int id = 1;

    @Override
    public String add(T entity) {
        String id = entity.getId();
        if(id == null || id.isEmpty()){
            id = String.valueOf(this.id++);
            entity.setId(id);
        }
		this.entidades.put(id, entity);
		
		return id;
    }

    @Override
    public void update(T entity) throws EntidadNoEncontrada {
    	
    	if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.entidades.put(entity.getId(), entity);

    }

    @Override
    public void delete(T entity) throws EntidadNoEncontrada {
    	
    	if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.entidades.remove(entity.getId());

    }

    @Override
    public T getById(String id) throws EntidadNoEncontrada {

    	if (! this.entidades.containsKey(id))
			throw new EntidadNoEncontrada(id + " no existe en el repositorio");
    	
    	return this.entidades.get(id);
    }

    @Override
    public List<T> getAll() {
    	
        return new ArrayList<>(entidades.values());
    }

    @Override
    public List<String> getIds() {
    	
        return new ArrayList<>(entidades.keySet());
    }
}
