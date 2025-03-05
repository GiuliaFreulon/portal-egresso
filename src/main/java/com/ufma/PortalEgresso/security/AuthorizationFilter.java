package com.ufma.PortalEgresso.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, java.io.IOException {
        String header = request.getHeader(SecurityConstants.HEADER_NAME);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}