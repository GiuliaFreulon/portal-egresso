package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.model.entity.Egresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DepoimentoRepo extends JpaRepository<Depoimento, UUID> {
    List<Depoimento> findByDataBetween(LocalDate start, LocalDate end);

    @Query("SELECT d FROM Depoimento d WHERE d.data >= :dataInicio ORDER BY d.data DESC")
    List<Depoimento> findRecent(@Param("dataInicio") LocalDate dataInicio);

    @Query("SELECT d FROM Depoimento d JOIN Egresso e ON d.egresso = e " +
            "JOIN CursoEgresso ce ON e = ce.egresso " +
            "WHERE LOWER(ce.curso.nome) LIKE LOWER(CONCAT('%', :cursoNome, '%'))")
    List<Depoimento> findByNomeDoCurso(String cursoNome);
}
