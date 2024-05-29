package com.arso.pasarela.config;

import com.arso.pasarela.interfaces.IJwtService;
import com.arso.pasarela.model.Rol;
import com.arso.pasarela.model.VerifyResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

@Component
public class ResponseFilter extends ZuulFilter {
    @Autowired
    IJwtService jwtService;

    @Override
    public String filterType() {
        return "post"; // Tipo de filtro: post para filtrar respuestas salientes
    }

    @Override
    public int filterOrder() {
        return 1; // Orden del filtro
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestMethod = ctx.getRequest().getMethod();
        // No aplicar el filtro para solicitudes OPTIONS
        return !"OPTIONS".equalsIgnoreCase(requestMethod);
    }

    @Override
    public Object run() {
        // Acciones a realizar en la respuesta
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestUri = ctx.getRequest().getRequestURI();

        if (requestUri.endsWith("/verify")) {
            handleVerifyUser(ctx);
        } else if (requestUri.contains("/signup-token")) {
            //handleVerifyUser(ctx);
        }

        return null;
    }

    private void handleVerifyUser(RequestContext ctx) {
        // Lógica para manejar el endpoint /users/verify

        InputStream responseDataStream = ctx.getResponseDataStream();
        String responseBody = null;
        try {
            if(ctx.getResponse().getStatus() < 300 && responseDataStream != null){
                responseBody = convertInputStreamToString(responseDataStream);
                if (responseBody != null && !responseBody.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
                    // Generar el token JWT con la respuesta y establecerlo como cuerpo de la respuesta
                    String token = jwtService.generateToken(responseMap);
                    Object rolValue = responseMap.get("rol");
                    Rol rol = null;
                    if (rolValue instanceof Integer) {
                        Integer rolInt = (Integer) rolValue;
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
                    }
                    VerifyResponse verifyResponse = VerifyResponse.builder()
                            .token(token)
                            .id(responseMap.get("id").toString())
                            .nombre(responseMap.get("name").toString())
                            .rol(rol)
                            .build();

                    String jsonResponse = objectMapper.writeValueAsString(verifyResponse);

                    ctx.setResponseBody(jsonResponse);
                    ctx.getResponse().setContentType("application/json");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
