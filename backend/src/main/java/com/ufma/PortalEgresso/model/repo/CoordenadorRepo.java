package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CoordenadorRepo extends JpaRepository<Coordenador, UUID> {
    Optional<Coordenador> findByLogin(String login);

    boolean existsByLogin(String email);
}
