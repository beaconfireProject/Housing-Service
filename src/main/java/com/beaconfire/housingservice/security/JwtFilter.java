package com.beaconfire.housingservice.security;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> claimsJws = jwtUtil.parseToken(token);
                Claims claims = claimsJws.getBody();

                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                Long userId = claims.get("userId", Long.class);

                if (username != null && role != null) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(authority));

                    AuthDetails authDetails = new AuthDetails(
                            userId,
                            role,
                            username,
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    authenticationToken.setDetails(authDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } catch (JwtException ex) {
                // Invalid/expired token — optional: log this
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}