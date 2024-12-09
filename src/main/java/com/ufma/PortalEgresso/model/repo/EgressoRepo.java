package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EgressoRepo extends JpaRepository<Egresso, UUID> {
    List<Egresso> findByDataBetween(LocalDate start, LocalDate end);

    @Query("SELECT e FROM Egresso e JOIN e.cargos c WHERE c.id_cargo = :idCargo")
    List<Egresso> findByCargoId(UUID idCargo);

    @Query("SELECT e FROM Egresso e JOIN CursoEgresso ce ON e.id_egresso = ce.egresso.id_egresso WHERE ce.curso.id_curso = :idCurso")
    List<Egresso> findByCursoId(UUID idCurso);

    boolean existsByEmail(String email);
}
