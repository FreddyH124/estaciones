package org.arso.services;

import org.arso.communication.ConsumidorEventos;
import org.arso.communication.Evento;
import org.arso.communication.PublicadorEventos;
import org.arso.communication.retrofit.estaciones.model.EstacionarBicicletaDTO;
import org.arso.factory.FactoriaRepositorios;
import org.arso.interfaces.IServicioAlquileres;
import org.arso.model.Alquiler;
import org.arso.model.Bicicleta;
import org.arso.model.Reserva;
import org.arso.model.Usuario;
import org.arso.repository.*;
import org.arso.factory.FactoriaServicios;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioAlquileres implements IServicioAlquileres {

    RepositorioUsuarios repositorio = FactoriaRepositorios.getRepositorio(Usuario.class);
    ConsumidorEventos consumidor = new ConsumidorEventos();
    PublicadorEventos publicador = new PublicadorEventos();

    @Override
    public Usuario getUsuario(String idUsuario) throws RepositorioException{
        try {
            return repositorio.getById(idUsuario);
        } catch (EntidadNoEncontrada e) {
            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            repositorio.add(usuario);
            return usuario;
        }
    }
    @Override
    public void reservarBicicleta(String idUsuario, String idBicicleta) throws IllegalStateException, RepositorioException {
        Usuario usuario = getUsuario(idUsuario);

        if(usuario.getReservaActiva() != null || usuario.getAlquilerActivo() != null){
            throw new IllegalStateException("El usuario ya tiene una reserva o alquiler en estado activo");
        }

        if(usuario.isBloqueado() || usuario.superaTiempoUso()){
            throw new IllegalStateException("El usuario esta bloqueado o supera el tiempo de uso");
        }

        Reserva reserva = new Reserva();
        reserva.setIdBicicleta(idBicicleta);
        reserva.setCreada(LocalDateTime.now());
        reserva.setCaducidad(LocalDateTime.now().plusMinutes(30));

        usuario.addReserva(reserva);

        //Todo actualizar usuario en la bbdd
        try {
            repositorio.update(usuario);
        } catch (EntidadNoEncontrada e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void confirmarReserva(String idUsuario) throws Exception {
        Usuario usuario = getUsuario(idUsuario);

        Reserva reserva = usuario.getReservaActiva();

        if(reserva == null){
            throw new IllegalStateException("El usuario no tiene una reserva pendiente de confirmar");
        }

        usuario.removeReserva(reserva);

        //Todo actualizar usuario en la bbdd
        try {
            repositorio.update(usuario);
        } catch (EntidadNoEncontrada e) {
            throw new RuntimeException(e);
        }

        alquilarBicicleta(idUsuario, reserva.getIdBicicleta());

    }

    @Override
    public void alquilarBicicleta(String idUsuario, String idBicicleta) throws Exception {
        Usuario usuario = getUsuario(idUsuario);

        if(usuario.getReservaActiva() != null || usuario.getAlquilerActivo() != null){
            throw new IllegalStateException("El usuario ya tiene una reserva o alquiler en estado activo");
        }

        if(usuario.isBloqueado() || usuario.superaTiempoUso()){
            throw new IllegalStateException("El usuario esta bloqueado o supera el tiempo de uso");
        }

        Alquiler alquiler = new Alquiler();
        alquiler.setIdBicicleta(idBicicleta);
        alquiler.setInicio(LocalDateTime.now());

        usuario.addAlquiler(alquiler);

        //Todo actualizar usuario en la bbdd
        try {
            repositorio.update(usuario);
        } catch (EntidadNoEncontrada e) {
            throw new RuntimeException(e);
        }
        
        //Se crea el evento y se envia
        Evento evento = new Evento("bicicleta-alquilada", new Date(), idBicicleta, "");
        publicador.publicarEvento(evento);
        
    }

    @Override
    public Usuario historialUsuario(String idUsuario) throws RepositorioException {
        Usuario usuario = getUsuario(idUsuario);
        return usuario;
    }

    @Override
    public void dejarBicicleta(String idUsuario, String idEstacion) throws Exception {
        Usuario usuario = getUsuario(idUsuario);

        Alquiler alquiler = usuario.getAlquilerActivo();

        if(alquiler == null){
            throw new IllegalStateException("El usuario no una reserva pendientes de confirmar");
        }

        alquiler.setFin(LocalDateTime.now());

        //Todo actualizar usuario en la bbdd
        try {
            repositorio.update(usuario);
        } catch (EntidadNoEncontrada e) {
            throw new RuntimeException(e);
        }
        
      //Se crea el evento y se envia
        Evento evento = new Evento("bicicleta-alquiler-concluido", new Date(), alquiler.getIdBicicleta(), idEstacion);
        publicador.publicarEvento(evento);
    }

    @Override
    public void liberarBloqueo(String idUsuario) throws RepositorioException {
        Usuario usuario = getUsuario(idUsuario);

        List<Reserva> lista = usuario.getReservas().stream()
                                .filter(Reserva::isCaducada)
                                .collect(Collectors.toList());

        for(Reserva r : lista){
            usuario.removeReserva(r);
        }

        //Todo actualizar usuario en la bbdd
        try {
            repositorio.update(usuario);
        } catch (EntidadNoEncontrada e) {
            throw new RuntimeException(e);
        }
    }
    
    public void eliminarReserva(String idBicicleta) throws RepositorioException {
    	if(idBicicleta == null) throw new IllegalArgumentException("idBicicleta vacío");
    	
    	List<Usuario> resultado = repositorio.buscarUsuariosPorBicicletaId(idBicicleta);
    	
    	if(resultado == null) throw new RepositorioException("no se han encontrado reservas");
    	
    	int index = 0;
    	
    	for (Usuario usuario : resultado) {
			if(usuario.getReservaActiva().getIdBicicleta().equals(idBicicleta))
				index = resultado.indexOf(usuario);
		}
    	
    	Usuario usuario = resultado.get(index);
    	
    	Reserva reserva = usuario.getReservaActiva();
    	
    	usuario.getReservas().remove(reserva);
    }
}
