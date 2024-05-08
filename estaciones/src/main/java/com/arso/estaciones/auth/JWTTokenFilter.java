package com.arso.estaciones.auth;

import com.arso.estaciones.model.Rol;
import com.arso.estaciones.service.JwtService;
import com.arso.estaciones.model.Usuario;
import com.mongodb.lang.NonNull;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor()
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService servicioUsuarios;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = jwtService.extractAllClaims(token);
            String username = claims.getSubject();


            int role = Integer.valueOf(claims.get("rol").toString());
            Rol rol = null;

            switch (role) {
                case -1:
                    rol = Rol.GESTOR;
                    break;
                case 0:
                    rol = Rol.NORMAL;
                    break;
                default:
                    throw new IllegalArgumentException("Valor de rol desconocido: " + role);
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(rol.name()));

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);



        /*final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        String userId;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authorizationHeader.substring(7);
        userId = jwtService.extractUsername(jwtToken);

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = servicioUsuarios.loadUserByUsername(userId);
            if(jwtService.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);*/
    }
}
