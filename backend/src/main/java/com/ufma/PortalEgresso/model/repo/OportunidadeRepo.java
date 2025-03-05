package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OportunidadeRepo extends JpaRepository<Oportunidade, UUID> {
}
