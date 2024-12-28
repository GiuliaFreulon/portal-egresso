package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.CursoEgresso;
import com.ufma.PortalEgresso.model.repo.CursoEgressoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoEgressoService {
    @Autowired
    private CursoEgressoRepo repo;

    @Transactional
    public CursoEgresso salvar(CursoEgresso cursoEgresso) {
        CursoEgresso salvo = repo.save(cursoEgresso);

        verificarCursoEgresso(cursoEgresso);

        return salvo;
    }

    @Transactional
    public CursoEgresso atualizar(CursoEgresso cursoEgresso) {
        verificarCursoEgresso(cursoEgresso);
        verificarId(cursoEgresso.getIdCursoEgresso());

        return salvar(cursoEgresso);
    }

    public Optional<CursoEgresso> buscarPorId(UUID id) {
        verificarId(id);

        return repo.findById(id);
    }

    public List<CursoEgresso> listarTodos() {
        List<CursoEgresso> lista = repo.findAll();

        if (lista.isEmpty()) {
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    @Transactional
    public void deletar(UUID id) {
        verificarId(id);

        repo.deleteById(id);
    }

    private void verificarId(UUID id) {

        if (id == null)
            throw new RegraNegocioRunTime("ID inválido");
        if (!repo.existsById(id)){
            throw new RegraNegocioRunTime("ID não encontrado");
        }
    }

    private void verificarCursoEgresso(CursoEgresso cursoEgresso) {
        if (cursoEgresso == null)
            throw new RegraNegocioRunTime("CursoEgresso inválido");

        if ((cursoEgresso.getAnoInicio() == null))
            throw new RegraNegocioRunTime("O ano de início do cursoEgresso deve estar preenchido");

        if ((cursoEgresso.getEgresso() == null))
            throw new RegraNegocioRunTime("O cursoEgresso deve estar associado a um egresso");

        if ((cursoEgresso.getCurso() == null))
            throw new RegraNegocioRunTime("O cursoEgresso deve estar associado a um curso");
    }
}
