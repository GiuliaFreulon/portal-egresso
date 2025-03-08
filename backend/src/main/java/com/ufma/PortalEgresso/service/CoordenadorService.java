package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ufma.PortalEgresso.model.entity.ENUMs.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @Autowired
    private CursoRepo cursoRepo;

    @Autowired
    private CursoEgressoRepo cursoEgressoRepo;

    @Autowired
    private DepoimentoRepo depoimentoRepo;

    @Autowired
    private OportunidadeRepo oportunidadeRepo;

    public boolean efetuarLogin(String login, String senha) {
        Optional<Coordenador> coordenador = repo.findByLogin(login);
        if ((!coordenador.isPresent()) || (!coordenador.get().getSenha().equals(senha)))
            throw new RegraNegocioRunTime("Erro de autenticação");

        return true;
    }

    @Transactional
    public Depoimento homologarDepoimento(Depoimento depoimento, Status status) {
        depoimento.setStatus(status);
        return depoimentoRepo.save(depoimento);
    }

    @Transactional
    public Oportunidade homologarOportunidade(Oportunidade oportunidade, Status status) {
        oportunidade.setStatus(status);
        return oportunidadeRepo.save(oportunidade);
    }

    @Transactional
    public Curso cadastrarCurso(String login, String nome, String nivel){

        Optional<Coordenador> coordenador = repo.findByLogin(login);

        if (coordenador.isEmpty()) {
            throw new RegraNegocioRunTime("Login inválido");
        }

        verificarId(coordenador.get().getId_coordenador());

        Curso curso = new Curso();

        curso.setCoordenador(coordenador.get());
        curso.setNome(nome);
        curso.setNivel(nivel);

        Curso cursoSalvo = cursoRepo.save(curso);

        return cursoSalvo;
    }

    @Transactional
    public Egresso cadastrarEgresso(Egresso egresso){
        try{
            Egresso salvo = egressoRepo.save(egresso);
            egressoRepo.flush();
            return salvo;
        } catch (DataIntegrityViolationException e){
            throw new RegraNegocioRunTime("Egresso inválido");
        }
    }

    @Transactional
    public Curso associarCursoAEgresso(Egresso egresso, Curso curso, Integer anoInicio, Integer anoFim){
        try{
            CursoEgresso.CursoEgressoBuilder builder = CursoEgresso.builder()
                    .egresso(egresso)
                    .curso(curso)
                    .anoInicio(anoInicio);

            if (anoFim != null) {
                builder.anoFim(anoFim);
            }

            CursoEgresso cursoEgresso = builder.build();

            egresso.getCursos().add(cursoEgresso);
            curso.getEgressos().add(cursoEgresso);

            CursoEgresso salvo = cursoEgressoRepo.save(cursoEgresso);
            cursoRepo.flush();
            return salvo.getCurso();
        } catch (DataIntegrityViolationException e){
            throw new RegraNegocioRunTime("Não foi possível associar curso a egresso");
        }

    }

    @Transactional
    public Coordenador salvar(Coordenador coordenador) {
        verificarCoordenador(coordenador);

        verificarLoginUnico(coordenador.getLogin(), coordenador.getId_coordenador());

        return repo.save(coordenador);
    }

    @Transactional
    public Coordenador atualizar(Coordenador coordenador) {
        verificarCoordenador(coordenador);

        verificarLoginUnico(coordenador.getLogin(), coordenador.getId_coordenador());

        verificarId(coordenador.getId_coordenador());

        return repo.save(coordenador);
    }

    public Optional<Coordenador> buscarPorId(UUID id) {
        verificarId(id);

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
        if (id == null)
            throw new RegraNegocioRunTime("ID inválido");
        if (!repo.existsById(id)){
            throw new RegraNegocioRunTime("ID não encontrado");
        }
    }

    private void verificarCoordenador(Coordenador coordenador) {
        if (coordenador == null)
            throw new RegraNegocioRunTime("Coordenador inválido");

        if ((coordenador.getLogin() == null) || (coordenador.getLogin().trim().isEmpty()))
            throw new RegraNegocioRunTime("O login do coordenador deve estar preenchido");

        if ((coordenador.getSenha() == null) || (coordenador.getSenha().trim().isEmpty()))
            throw new RegraNegocioRunTime("A senha do coordenador deve estar preenchida");
    }

    private void verificarLoginUnico(String login, UUID id) {
        try {
            boolean existsByLogin = repo.existsByLogin(login);

            if(existsByLogin){
                Coordenador existente = repo.findByLogin(login).orElse(null);

                if(existente != null && !existente.getId_coordenador().equals(id)) {
                    throw new RegraNegocioRunTime("Já existe um coordenador com esse login. Por favor, utilize um login diferente");
                }
            }
        } catch (DataIntegrityViolationException ex) {
            throw new RegraNegocioRunTime("Já existe um coordenador com esse login. Por favor, utilize um login diferente");
        }
    }

    //UserDetailsService

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Coordenador> coordenador = repo.findByLogin(login);
        if (!coordenador.isPresent()) {
            throw new UsernameNotFoundException(login);
        }

        Coordenador recuperado = coordenador.get();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_COORDENADOR"));

        return new User(recuperado.getLogin(), recuperado.getSenha(), authorities);
    }

}
