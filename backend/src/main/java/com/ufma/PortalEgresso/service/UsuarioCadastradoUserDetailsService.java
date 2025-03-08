package com.ufma.PortalEgresso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCadastradoUserDetailsService implements UserDetailsService {
    @Autowired
    EgressoService egressoService;

    @Autowired
    CoordenadorService coordenadorService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // verifica se o email pertence a um egresso ou coordenador
        try {
            return coordenadorService.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            return egressoService.loadUserByUsername(login);
        }
    }
}
