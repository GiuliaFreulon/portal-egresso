package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Coordenador;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.repo.CoordenadorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoordenadorService {
    @Autowired
    private CoordenadorRepo repo;

    public boolean efetuarLogin(String login, String senha) {
        Optional<Coordenador> coordenador = repo.findByLogin(login);
        if ((!coordenador.isPresent()) || (!coordenador.get().getSenha().equals(senha)))
            throw new RegraNegocioRunTime("Erro de autenticação");

        return true;
    }

    @Transactional
    public boolean cadastrarCurso(String login, String nome, String nivel){
        Optional<Coordenador> coordenador = repo.findByLogin(login);

        CursoService service = new CursoService();
        Curso curso = new Curso();

        curso.setCoordenador(coordenador.get());
        curso.setNome(nome);
        curso.setNivel(nivel);

        service.salvar(curso);

        return true;
    }

    @Transactional
    public Coordenador salvar(Coordenador coordenador) {
        Coordenador salvo = repo.save(coordenador);

        verificarCoordenador(coordenador);

        return salvo;
    }

    @Transactional
    public Coordenador atualizar(Coordenador coordenador) {
        verificarCoordenador(coordenador);

        return salvar(coordenador);
    }

    public Optional<Coordenador> buscarPorId(UUID id) {
        if (!repo.existsById(id)){
            throw new BuscaVaziaRunTime();
        }

        return repo.findById(id);
    }

    public List<Coordenador> listarTodos() {
        List<Coordenador> lista = repo.findAll();

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

    private void verificarCoordenador(Coordenador coordenador) {
        if (coordenador == null)
            throw new RegraNegocioRunTime("Coordenador inválido");

        if ((coordenador.getId_coordenador() == null))
            throw new RegraNegocioRunTime("O ID do coordenador deve estar preenchido");

        if ((coordenador.getTipo() == null) || (coordenador.getTipo().trim().isEmpty()))
            throw new RegraNegocioRunTime("O tipo do coordenador deve estar preenchido");

        if ((coordenador.getLogin() == null) || (coordenador.getLogin().trim().isEmpty()))
            throw new RegraNegocioRunTime("O login do coordenador deve estar preenchido");

        if ((coordenador.getSenha() == null) || (coordenador.getSenha().trim().isEmpty()))
            throw new RegraNegocioRunTime("A senha do coordenador deve estar preenchida");
    }

}
