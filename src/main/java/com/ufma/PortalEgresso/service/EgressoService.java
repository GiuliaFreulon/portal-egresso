package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EgressoService {
    @Autowired
    private EgressoRepo repo;

    @Transactional
    public Egresso salvar(Egresso egresso) {
        Egresso salvo = repo.save(egresso);

        verificarEgresso(egresso);

        verificarEmailUnico(egresso.getEmail());

        try {
            return salvo;
        } catch (ConstraintViolationException e) {
            // Trata o erro de e-mail inválido
            throw new RegraNegocioRunTime("E-mail inválido. Verifique o formato.");
        }
    }

    @Transactional
    public Egresso atualizar(Egresso egresso) {
        verificarEgresso(egresso);

        Egresso egressoExistente = repo.findById(egresso.getId_egresso())
                .orElseThrow(() -> new RegraNegocioRunTime("Egresso não encontrado"));

        // Verifica se o e-mail foi alterado
        if (!egressoExistente.getEmail().equals(egresso.getEmail())) {
            verificarEmailUnico(egresso.getEmail());
        }

        return repo.save(egresso);
    }

    public Optional<Egresso> buscarPorId(UUID id) {
        if (!repo.existsById(id)){
            throw new BuscaVaziaRunTime();
        }

        return repo.findById(id);
    }

    public List<Egresso> buscarPorCurso(Curso curso) {
        List<Egresso> lista = repo.findByCursoId(curso.getId_curso());

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    public List<Egresso> buscarPorCargo(Cargo cargo) {
        List<Egresso> lista = repo.findByCargoId(cargo.getId_cargo());

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    public List<Egresso> buscarPorAno(Integer ano) {
        LocalDate inicioAno = LocalDate.of(ano, 1, 1);
        LocalDate fimAno = LocalDate.of(ano, 12, 31);
        List<Egresso> lista = repo.findByDataBetween(inicioAno, fimAno);

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    public List<Egresso> listarTodos() {
        List<Egresso> lista = repo.findAll();

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
        if ((id == null) || !repo.existsById(id))
            throw new RegraNegocioRunTime("ID inválido ou não encontrado");
    }

    private void verificarEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRunTime("Egresso inválido");

        if ((egresso.getId_egresso() == null))
            throw new RegraNegocioRunTime("O ID do egresso deve estar preenchido");

        if ((egresso.getNome() == null) || (egresso.getNome().trim().isEmpty()))
            throw new RegraNegocioRunTime("O nome do egresso deve estar preenchido");

        if ((egresso.getEmail() == null) || (egresso.getEmail().trim().isEmpty()))
            throw new RegraNegocioRunTime("O e-mail do egresso deve estar preenchido");

    }

    private void verificarEmailUnico(String email) {
        if (repo.existsByEmail(email)) {
            throw new RegraNegocioRunTime("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente.");
        }
    }
}
