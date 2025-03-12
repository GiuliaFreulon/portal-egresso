package com.ufma.PortalEgresso.model.entity.DTOs;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastradoDTO implements UserDetails {
    private UUID id;
    private String email;
    private String login;
    private String username;
    private String senha;
    private String password;
    private Collection<? extends GrantedAuthority> getAuthorities;

    public UsuarioCadastradoDTO(UUID id, String login, String senha, List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.email = login;
        this.login = login;
        this.username = login;
        this.senha = senha;
        this.password = senha;
        this.getAuthorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities;
    }
}
