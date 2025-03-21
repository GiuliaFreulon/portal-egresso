package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.DTOs.EgressoResumoDTO;
import com.ufma.PortalEgresso.model.entity.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EgressoRepo extends JpaRepository<Egresso, UUID> {

    @Query("SELECT e FROM Egresso e JOIN e.cargos c WHERE c.id_cargo = :idCargo")
    List<Egresso> findByCargoId(UUID idCargo);

    @Query("SELECT e FROM Egresso e JOIN CursoEgresso ce ON e.id_egresso = ce.egresso.id_egresso WHERE ce.curso.id_curso = :idCurso")
    List<Egresso> findByCursoId(UUID idCurso);

    @Query("SELECT e FROM Egresso e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nomeEgresso, '%'))")
    List<Egresso> findByNome(String nomeEgresso);

    @Query("SELECT NEW com.ufma.PortalEgresso.model.entity.DTOs.EgressoResumoDTO(e.id_egresso, e.nome, e.foto) FROM Egresso e")
    List<EgressoResumoDTO> findNomeAndIdOfAll();

    Optional<Egresso> findByEmail(String email);

    boolean existsByEmail(String email);

}
