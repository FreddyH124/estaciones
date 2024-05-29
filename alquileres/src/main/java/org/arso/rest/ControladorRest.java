package org.arso.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.arso.factory.FactoriaServicios;
import org.arso.interfaces.IServicioAlquileres;

import io.jsonwebtoken.Claims;
import org.arso.model.DTO.UsuarioResumenDTO;
import org.arso.model.Usuario;

@Path("alquileres")
public class ControladorRest {
	
	private IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpServletRequest servletRequest;

	@OPTIONS
	@Path("{path:.*}")
	public Response options() {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS")
				.header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept")
				.build();
	}


	@POST
	@Path("bicicletas/{idUsuario}/reservar/{idBicicleta}")
	@RolesAllowed("NORMAL")
	public Response reservarBicileta( @PathParam("idUsuario") String idUsuario,
									  @PathParam("idBicicleta") String idBicicleta) throws Exception {
		Response r = Response.status(Response.Status.NO_CONTENT).build();
		try {
			servicioAlquileres.reservarBicicleta(idUsuario, idBicicleta);
			return Response.status(Response.Status.CREATED).build();
		} catch (IllegalArgumentException e) {

			Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	
	@POST
	@Path("reservas/{idUsuario}/confirmar")
	@RolesAllowed("NORMAL")
	public Response confirmarReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
		Response r = Response.status(Response.Status.NO_CONTENT).build();
		try {
			servicioAlquileres.confirmarReserva(idUsuario);
			return Response.status(Response.Status.OK).build();
		} catch (IllegalStateException e) {
			r = Response.status(Response.Status.BAD_REQUEST).build();
		}

		return r;
	}
	
	@POST
	@Path("bicicletas/{idUsuario}/alquilar/{idBicicleta}")
	@RolesAllowed("NORMAL")
	public Response alquilarBicicleta(@PathParam("idUsuario") String idUsuario,
									  @PathParam("idBicicleta") String idBicicleta) throws Exception {
		Response r = Response.status(Response.Status.NO_CONTENT).build();
		try {
			servicioAlquileres.alquilarBicicleta(idUsuario, idBicicleta);
		} catch (IllegalStateException e) {
			r = Response.status(Response.Status.BAD_REQUEST).build();
		}

		return r;
	}
	
	@POST
	@Path("activo/{idUsuario}/finalizar/{idEstacion}")
	@RolesAllowed("NORMAL")
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario,
								   @PathParam("idEstacion") String idEstacion) throws Exception {
		try {
			servicioAlquileres.dejarBicicleta(idUsuario, idEstacion);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@PATCH
	@Path("usuarios/{idUsuario}/liberar")
	@RolesAllowed("GESTOR")
	public Response liberarBloqueo(@PathParam("idUsuario") String idUsuario) throws Exception {
		try {
			servicioAlquileres.liberarBloqueo(idUsuario);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("usuarios/{idUsuario}/historial")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("NORMAL")
	public Response getHistorialUsuario( @PathParam("idUsuario") String idUsuario) throws Exception {

		try {
			Usuario usuario = servicioAlquileres.historialUsuario(idUsuario);
			UsuarioResumenDTO resumen = new UsuarioResumenDTO(usuario.getReservas(), usuario.getAlquileres(),
																usuario.isBloqueado(), usuario.getTiempoUsoHoy(), usuario.getTiempoUsoSemanal());
			return Response.status(Response.Status.OK).entity(resumen).build();
		} catch (IllegalArgumentException e) {

			Response.status(Response.Status.BAD_REQUEST).build();
		}

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
	
	

}
