package com.arso.estaciones.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    //String extractUsername(String token);

   //Date extractExpiration(String token);

    //<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);

    //Boolean isTokenExpired(String token);

    //Boolean validateToken(String token, UserDetails userDetails);

    //String generateToken(UserDetails userDetails);

    //String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    //Key getSignKey();
}
