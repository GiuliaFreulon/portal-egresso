package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Discussao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiscussaoRepo extends JpaRepository<Discussao, UUID> {
}
