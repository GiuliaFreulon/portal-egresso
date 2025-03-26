package com.ufma.PortalEgresso.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationFilter authenticationFilter,
            AuthorizationFilter authorizationFilter
    ) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/egresso/listarTodos").permitAll()
                        .requestMatchers("/api/egresso/listarNomeIdTodos").permitAll()
                        .requestMatchers("/api/egresso/buscarPorId/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/curso/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/depoimento/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/oportunidade/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/egresso/**").hasAnyAuthority("ROLE_COORDENADOR", "ROLE_EGRESSO")
                        .requestMatchers(HttpMethod.DELETE, "/api/curso/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/egresso/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/depoimento/**").hasAuthority("ROLE_EGRESSO")
                        .requestMatchers(HttpMethod.DELETE, "/api/oportunidade/**").hasAuthority("ROLE_EGRESSO")
                        .requestMatchers(HttpMethod.DELETE, "/api/discussao/**").hasAnyAuthority("ROLE_COORDENADOR", "ROLE_EGRESSO")
                        .requestMatchers("/api/egresso/**").hasAuthority("ROLE_EGRESSO")
                        .requestMatchers("/api/coordenador/**").hasAuthority("ROLE_COORDENADOR")
                        .requestMatchers("/api/**","/login","/**").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    // Bean para AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuração de CORS (opcional)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
