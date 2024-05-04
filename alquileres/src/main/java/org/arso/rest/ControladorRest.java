package org.arso.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.arso.factory.FactoriaServicios;
import org.arso.interfaces.services.IServicioAlquileres;

import io.jsonwebtoken.Claims;

@Path("alquileres")
public class ControladorRest {
	
	private IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpServletRequest servletRequest;
	
	//Poner @PermitAll en el servicio de login
	//http://localhost:8080/api/alquileres/1
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("administrador")
	public Response getUsuario( @PathParam("id") String id) throws Exception {
		
		if (this.servletRequest.getAttribute("claims") != null) {
			Claims claims = (Claims) this.servletRequest.getAttribute("claims");
			System.out.println("Usuario autenticado: " + claims.getSubject());
			System.out.println("Roles: " + claims.get("roles"));
			}
		
		try {
			servicioAlquileres.getUsuario(id);
		} catch (IllegalArgumentException e) {
			
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.OK).entity(servicioAlquileres.getUsuario(id)).build();
		
		
	}
	
	@POST
	@Path("{id}/reservas")
	public Response reservarBicileta( @PathParam("id") String id,
			String idBicicleta) throws Exception {
		
		try {
			servicioAlquileres.reservarBicicleta(id, idBicicleta);
		} catch (IllegalArgumentException e) {
			
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	
	@POST
	@Path("{id}/reservas")
	public Response confirmarReserva(@PathParam("id") String id) throws Exception {
		try {
			servicioAlquileres.confirmarReserva(id);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@POST
	@Path("{id}/reserva/alquileres")
	public Response alquilarBicicleta(@PathParam("id") String id,
		String idBicicleta) throws Exception {
		try {
			servicioAlquileres.reservarBicicleta(id, idBicicleta);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@POST
	@Path("{id}/alquiler-activo")
	public Response dejarBicicleta(@PathParam("id") String id,
		String idEstacion) throws Exception {
		try {
			servicioAlquileres.dejarBicicleta(id, idEstacion);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@PATCH
	@Path("{id}")
	public Response liberarBloqueo(@PathParam("id") String id) throws Exception {
		try {
			servicioAlquileres.liberarBloqueo(id);
		} catch (IllegalStateException e) {
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	
	

}
