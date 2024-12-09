package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CargoRepo extends JpaRepository<Cargo, UUID> {
}
