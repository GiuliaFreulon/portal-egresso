package com.ufma.PortalEgresso.model.repo;

import com.ufma.PortalEgresso.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CursoRepo extends JpaRepository<Curso, UUID> {
    @Query("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCurso, '%'))")
    List<Curso> findByNome(String nomeCurso);
}
