package org.arso.factory;


import org.arso.interfaces.repository.IRepositorio;
import org.arso.utils.PropertiesReader;

public class FactoriaRepositorios {
	
private static final String PROPERTIES = "repositorios.properties";
	
	
	@SuppressWarnings("unchecked")
	public static <K, T, R extends IRepositorio<K, T>> R getRepositorio(Class<?> entidad) {
				
			
			try {				
					PropertiesReader properties = new PropertiesReader(PROPERTIES);
					String clase = properties.getProperty(entidad.getName());
					return (R) Class.forName(clase).getConstructor().newInstance();
			}
			catch (Exception e) {
				
				e.printStackTrace(); // útil para depuración
				
				throw new RuntimeException("No se ha podido obtener el repositorio para la entidad: " + entidad.getName());
			}
			
	}

}
