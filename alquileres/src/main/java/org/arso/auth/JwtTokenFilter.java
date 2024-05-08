package org.arso.auth;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import org.arso.factory.FactoriaServicios;
import org.arso.interfaces.IJwtService;
import org.arso.model.Rol;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.Priorities;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	@Context
	private HttpServletRequest servletRequest; // Peticion a bajo nivel

	private IJwtService jwtService = FactoriaServicios.getServicio(IJwtService .class);

	@Override
	public void filter(ContainerRequestContext requestContext) {

		// Comprobamos si la ruta tiene la anotación @PermitAll
		if (resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)) {
			return;
		}

		// Implementación del control de autorización
		String authorization = requestContext.getHeaderString("Authorization");
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		} else {
			String token = authorization.substring("Bearer ".length()).trim();
			try {
				Claims claims = jwtService.extractAllClaims(token);

				this.servletRequest.setAttribute("claims", claims);

				Date caducidad = claims.getExpiration();

				if (caducidad.before(new Date()))
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

				// Control por roles
				int rolInt = claims.get("rol", Integer.class);


				Rol rol = null;

				switch (rolInt) {
					case -1:
						rol = Rol.GESTOR;
						break;
					case 0:
						rol = Rol.NORMAL;
						break;
					default:
						throw new IllegalArgumentException("Valor de rol desconocido: " + rolInt);
				}

				Set<String> authorities = new HashSet<>();
				authorities.add(rol.name());

				if (this.resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {

					String[] allowedRoles = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class).value();

					if (authorities.stream().noneMatch(userRole -> Arrays.asList(allowedRoles).contains(userRole))) {
						requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
					}
				}

			} catch (Exception e) { // Error de validación
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		}

	}
}
