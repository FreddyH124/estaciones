package org.arso.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.arso.interfaces.IJwtService;

public class JwtService implements IJwtService {
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
