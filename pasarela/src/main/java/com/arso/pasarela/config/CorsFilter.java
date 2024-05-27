package com.arso.pasarela.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletResponse;

public class CorsFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post"; // Este filtro se ejecutará después de que se haya realizado el enrutamiento.
    }

    @Override
    public int filterOrder() {
        return 1000; // Orden de ejecución del filtro
    }

    @Override
    public boolean shouldFilter() {
        return true; // El filtro se ejecutará para todas las solicitudes.
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        return null;
    }
}