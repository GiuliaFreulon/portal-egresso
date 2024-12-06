package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.CursoEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso, UUID> {
}
