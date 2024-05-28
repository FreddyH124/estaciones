package org.arso.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        // Verifica si es una solicitud OPTIONS y ajusta el código de estado
        if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
            responseContext.setStatus(Response.Status.OK.getStatusCode()); // Asegúrate de devolver 200 OK
        }
    }
}
