package org.arso.interfaces;

import io.jsonwebtoken.Claims;

public interface IJwtService {
    Claims extractAllClaims(String token);
}
