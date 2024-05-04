package org.arso.auth;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("auth")
public class ControladorAuth {
	
	@POST
	@Path("/login")
	public Response login(
		@FormParam("username") String username,
		@FormParam("password") String password) {
		
		Map<String, Object> claims = verificarCredenciales(username, password);
		if (claims != null) {
			Date caducidad = Date.from(
					Instant.now()
					.plusSeconds(3600));  // Tiempo de validez
			String token = Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS256, "secreto")
					.setExpiration(caducidad).compact();
			
			return Response.ok(token).build();
		} else {
			return Response
				.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
		}
		
	}
	
	
	private Map<String, Object> verificarCredenciales(String username, String password) {
		
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", username);
		claims.put("roles", "administrador");
		
		return claims;
	}
	
	//TODO terminar proceso y hacer uno para el usuario normal

}
