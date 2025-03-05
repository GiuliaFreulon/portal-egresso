package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.ENUMs.Status;
import com.ufma.PortalEgresso.model.entity.Oportunidade;
import com.ufma.PortalEgresso.model.repo.OportunidadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepo repo;

    @Transactional
    public Oportunidade salvar(Oportunidade oportunidade) {
        verificarOportunidade(oportunidade);

        if (oportunidade.getStatus() == null) {
            oportunidade.setStatus(Status.AGUARDANDO);
        }

        Oportunidade salvo = repo.save(oportunidade);

        return salvo;
    }

    @Transactional
    public Oportunidade atualizar(Oportunidade oportunidade) {
        verificarOportunidade(oportunidade);
        verificarId(oportunidade.getId_oportunidade());

        return repo.save(oportunidade);
    }

    public Optional<Oportunidade> buscarPorId(UUID id) {
        verificarId(id);

        return repo.findById(id);
    }

    public List<Oportunidade> listarTodos() {
        List<Oportunidade> lista = repo.findAll();

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

    private void verificarOportunidade(Oportunidade oportunidade) {
        if (oportunidade == null)
            throw new RegraNegocioRunTime("Discussão inválida");

        if (oportunidade.getEgresso() == null)
            throw new RegraNegocioRunTime("A oportunidade deve estar associada a um egresso");

        if ((oportunidade.getTitulo() == null) || (oportunidade.getTitulo().trim().isEmpty()))
            throw new RegraNegocioRunTime("A oportunidade deve possuir um título");

        if ((oportunidade.getDescricao() == null) || (oportunidade.getDescricao().trim().isEmpty()))
            throw new RegraNegocioRunTime("A descrição da oportunidade não pode estar vazia");
    }
}
