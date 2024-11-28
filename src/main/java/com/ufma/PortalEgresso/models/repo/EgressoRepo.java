package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EgressoRepo extends JpaRepository<Egresso, UUID> {
}
