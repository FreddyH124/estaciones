package org.arso.tests;

import org.arso.model.Bicicleta;
import org.arso.model.Reserva;
import org.arso.model.Usuario;
import org.arso.repository.RepositorioException;
import org.arso.repository.RepositorioUsuarios;
import org.arso.services.ServicioAlquileres;

public class TestQuery {

	public static void main(String[] args) throws IllegalStateException, RepositorioException {
		ServicioAlquileres s = new ServicioAlquileres();
		/*Usuario u = new Usuario();
		u.setId("3");
		Bicicleta b = new Bicicleta();
		b.setId("ADondeVasConTantaMunicion");
		Reserva r = new Reserva();
		
		u.addReserva(r);
		s.reservarBicicleta(u.getId(), b.getId());*/
		
		RepositorioUsuarios repo = new RepositorioUsuarios();
		
		System.out.println(repo.buscarUsuariosPorBicicletaId("YoquieroBailar")); //Guay del paraguay
	}

}
