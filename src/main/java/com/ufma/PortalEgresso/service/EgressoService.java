package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EgressoService {
    @Autowired
    EgressoRepo repo;

    // TODO: Fazer autocadastro
    @Transactional
    public Egresso autoCadastrar(String nome, String email, String senha){
        Egresso egresso = new Egresso();

        egresso.setNome(nome);
        egresso.setNome(email);
        egresso.setSenha(senha);

        verificarEgresso(egresso);
        verificarEmailUnico(egresso.getEmail(), egresso.getId_egresso());

        Egresso egressoSalvo = repo.save(egresso);

        return egressoSalvo;
    }
    // TODO: Integração com redes sociais

    public boolean efetuarLogin(String login, String senha) {
        Optional<Egresso> egresso = repo.findByEmail(login);
        if ((!egresso.isPresent()) || (!egresso.get().getSenha().equals(senha)))
            throw new RegraNegocioRunTime("Erro de autenticação");

        return true;
    }

    @Transactional
    public Egresso salvar(Egresso egresso) {

        verificarEgresso(egresso);

        verificarEmailUnico(egresso.getEmail(), egresso.getId_egresso());

        Egresso salvo = repo.save(egresso);

        return salvo;
    }

    @Transactional
    public Egresso atualizar(Egresso egresso) {

        verificarEgresso(egresso);
        verificarEmailUnico(egresso.getEmail(), egresso.getId_egresso());

        verificarId(egresso.getId_egresso());

        return repo.save(egresso);
    }

    public Optional<Egresso> buscarPorId(UUID id) {
        verificarId(id);
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

    @Transactional
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
        if (id == null)
            throw new RegraNegocioRunTime("ID inválido");
        if (!repo.existsById(id)){
            throw new RegraNegocioRunTime("ID não encontrado");
        }
    }


    private void verificarEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRunTime("Egresso inválido");

        if ((egresso.getNome() == null) || (egresso.getNome().trim().isEmpty()))
            throw new RegraNegocioRunTime("O nome do egresso deve estar preenchido");

        if ((egresso.getEmail() == null) || (egresso.getEmail().trim().isEmpty()))
            throw new RegraNegocioRunTime("O e-mail do egresso deve estar preenchido");

        if ((egresso.getSenha() == null) || (egresso.getSenha().trim().isEmpty()))
            throw new RegraNegocioRunTime("A senha do egresso deve estar preenchida");
    }

    private void verificarEmailUnico(String email, UUID id) {
        try {
            boolean emailExiste = repo.existsByEmail(email);

            if(emailExiste){
                Egresso existente = repo.findByEmail(email).orElse(null);

                if(existente != null && !existente.getId_egresso().equals(id)) {
                    throw new RegraNegocioRunTime("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
                }
            }
        } catch (DataIntegrityViolationException ex) {
            throw new RegraNegocioRunTime("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
        }
    }

}
