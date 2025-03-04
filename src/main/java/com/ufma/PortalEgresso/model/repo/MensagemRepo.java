package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface MensagemRepo extends JpaRepository<Mensagem, UUID> {
    @Query("SELECT m FROM Mensagem m WHERE m.discussao.id_discussao = :idDiscussao ORDER BY m.dataEnvio ASC")
    List<Mensagem> findByGrupoIdOrderByDataEnvioAsc(UUID idDiscussao);
}
