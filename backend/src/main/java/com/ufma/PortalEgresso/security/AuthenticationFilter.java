package com.ufma.PortalEgresso.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.model.entity.DTOs.UsuarioCadastradoDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager); // Chama o construtor da classe pai
        setFilterProcessesUrl("/login"); // Define o endpoint de login
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            UsuarioCadastradoDTO requestDTO = new ObjectMapper().readValue(request.getInputStream(), UsuarioCadastradoDTO.class);
            String login = requestDTO.getEmail() != null ? requestDTO.getEmail() : requestDTO.getLogin();
            return this.getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login,
                            requestDTO.getSenha(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException | java.io.IOException e) {
            throw new AuthenticationServiceException("Erro ao ler credenciais", e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {

        // Obtém as roles do usuário
        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("roles", roles) // Adiciona as roles ao token
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SecurityConstants.KEY)
                .compact();

        response.addHeader(SecurityConstants.HEADER_NAME, "Bearer " + token);
    }
}