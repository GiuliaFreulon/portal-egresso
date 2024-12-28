package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoService {
    @Autowired
    private CursoRepo repo;

    @Transactional
    public Curso salvar(Curso curso) {
        verificarCurso(curso);

        Curso salvo = repo.save(curso);

        return salvo;
    }

    @Transactional
    public Curso atualizar(Curso curso) {
        verificarId(curso.getId_curso());
        verificarCurso(curso);

        return repo.save(curso);
    }

    public Optional<Curso> buscarPorId(UUID id) {
        verificarId(id);

        return repo.findById(id);
    }

    public List<Curso> listarTodos() {
        List<Curso> lista = repo.findAll();

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    @Transactional
    public void deletar(UUID id){
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

    private void verificarCurso(Curso curso) {
        if (curso == null)
            throw new RegraNegocioRunTime("Curso inválido");

        if ((curso.getCoordenador() == null))
            throw new RegraNegocioRunTime("O coordenador do curso deve estar preenchido");

        if ((curso.getNome() == null) || (curso.getNome().trim().isEmpty()))
            throw new RegraNegocioRunTime("O nome do curso deve estar preenchido");

        if ((curso.getNivel() == null) || (curso.getNivel().trim().isEmpty()))
            throw new RegraNegocioRunTime("O nível do curso deve estar preenchido");
    }

}
