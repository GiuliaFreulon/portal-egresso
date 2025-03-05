package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.repo.CargoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CargoService {
    @Autowired
    private CargoRepo repo;

    @Transactional
    public Cargo salvar(Cargo cargo) {
        verificarCargo(cargo);
        Cargo salvo = repo.save(cargo);

        return salvo;
    }

    @Transactional
    public Cargo atualizar(Cargo cargo) {
        verificarCargo(cargo);
        verificarId(cargo.getId_cargo());

        return repo.save(cargo);
    }

    public Optional<Cargo> buscarPorId(UUID id) {
        verificarId(id);

        return repo.findById(id);
    }

    public List<Cargo> listarTodos() {
        List<Cargo> lista = repo.findAll();

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

    private void verificarCargo(Cargo cargo) {
        if (cargo == null)
            throw new RegraNegocioRunTime("Cargo inválido");

        if ((cargo.getLocal() == null) || (cargo.getLocal().trim().isEmpty()))
            throw new RegraNegocioRunTime("O local do cargo deve estar preenchido");

        if ((cargo.getDescricao() == null) || (cargo.getDescricao().trim().isEmpty()))
            throw new RegraNegocioRunTime("A descrição do cargo deve estar preenchida");

        if ((cargo.getAnoInicio() == null))
            throw new RegraNegocioRunTime("O ano de início do cargo deve estar preenchido");

        if ((cargo.getEgresso() == null))
            throw new RegraNegocioRunTime("O cargo deve estar associado a um egresso");
    }

}
