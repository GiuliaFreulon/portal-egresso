package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepo extends JpaRepository<Curso, UUID> {
}
