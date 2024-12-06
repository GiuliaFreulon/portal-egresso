package com.ufma.PortalEgresso.models.repo;

import com.ufma.PortalEgresso.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CargoRepo extends JpaRepository<Cargo, UUID> {
}
