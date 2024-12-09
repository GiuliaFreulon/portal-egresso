package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.CursoEgresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoEgressoRepo extends JpaRepository<CursoEgresso, UUID> {
}
