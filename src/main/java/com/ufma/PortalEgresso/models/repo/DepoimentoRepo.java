package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepoimentoRepo extends JpaRepository<Depoimento, UUID> {
}
