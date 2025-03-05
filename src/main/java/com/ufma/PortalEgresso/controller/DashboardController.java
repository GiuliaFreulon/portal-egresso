package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.model.entity.CursoEgresso;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.repo.CursoEgressoRepo;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final CursoEgressoRepo cursoEgressoRepository;
    private final CursoRepo cursoRepository;

    // Egressos por Curso
    @GetMapping("/egressosPorCurso")
    public Map<String, Long> getEgressosPorCurso() {
        List<CursoEgresso> cursoEgressoList = cursoEgressoRepository.findAll();
        return cursoEgressoList.stream()
                .collect(Collectors.groupingBy(
                        ce -> ce.getCurso().getNome(),
                        Collectors.counting()
                ));
    }

    // Egressos por Nível do Curso
    @GetMapping("/egressosPorNivel")
    public Map<String, Long> getEgressosPorNivel() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .collect(Collectors.groupingBy(
                        Curso::getNivel,
                        Collectors.summingLong(curso -> (long) curso.getEgressos().size())
                ));
    }

    // Empregabilidade por Curso (Egressos com cargo X Egressos sem cargo)
    @GetMapping("/empregabilidadePorCurso")
    public Map<String, Map<String, Long>> getEmpregabilidadePorCurso() {
        List<CursoEgresso> cursoEgressoList = cursoEgressoRepository.findAll();

        Map<String, Map<String, Long>> resultado = new HashMap<>();

        for (CursoEgresso cursoEgresso : cursoEgressoList) {
            String nomeCurso = cursoEgresso.getCurso().getNome();
            boolean temCargo = !cursoEgresso.getEgresso().getCargos().isEmpty();

            resultado.putIfAbsent(nomeCurso, new HashMap<>());
            resultado.get(nomeCurso).put("Empregados", resultado.get(nomeCurso).getOrDefault("Empregados", 0L) + (temCargo ? 1 : 0));
            resultado.get(nomeCurso).put("Não Empregados", resultado.get(nomeCurso).getOrDefault("Não Empregados", 0L) + (temCargo ? 0 : 1));
        }

        return resultado;
    }
}