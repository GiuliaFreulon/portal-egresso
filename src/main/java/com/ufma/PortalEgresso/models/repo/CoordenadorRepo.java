package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoordenadorRepo extends JpaRepository<Coordenador, UUID> {
}
