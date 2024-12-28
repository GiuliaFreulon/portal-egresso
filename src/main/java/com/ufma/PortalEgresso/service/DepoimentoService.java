package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.model.repo.DepoimentoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepoimentoService {
    @Autowired
    private DepoimentoRepo repo;

    @Transactional
    public Depoimento salvar(Depoimento depoimento) {
        verificarDepoimento(depoimento);
        Depoimento salvo = repo.save(depoimento);

        return salvo;
    }

    @Transactional
    public Depoimento atualizar(Depoimento depoimento) {
        verificarDepoimento(depoimento);
        verificarId(depoimento.getId_depoimento());

        return salvar(depoimento);
    }

    public Optional<Depoimento> buscarPorId(UUID id) {
        verificarId(id);

        return repo.findById(id);
    }

    public List<Depoimento> buscarPorAno(Integer ano) {
        LocalDate inicioAno = LocalDate.of(ano, 1, 1);
        LocalDate fimAno = LocalDate.of(ano, 12, 31);
        List<Depoimento> lista = repo.findByDataBetween(inicioAno, fimAno);

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    public List<Depoimento> buscarRecentes() {
        LocalDate trintaDiasAtras = LocalDate.now().minusDays(30);

        List<Depoimento> lista = repo.findRecent(trintaDiasAtras);

        if (lista.isEmpty()){
            throw new BuscaVaziaRunTime();
        }

        return lista;
    }

    public List<Depoimento> listarTodos() {
        List<Depoimento> lista = repo.findAll();

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

    private void verificarDepoimento(Depoimento depoimento) {
        if (depoimento == null)
            throw new RegraNegocioRunTime("Depoimento inválido");

        if ((depoimento.getEgresso() == null))
            throw new RegraNegocioRunTime("O depoimento deve estar associado a um egresso");
    }
}
