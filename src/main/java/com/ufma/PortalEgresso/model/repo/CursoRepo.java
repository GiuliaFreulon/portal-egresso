package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepo extends JpaRepository<Curso, UUID> {
}
