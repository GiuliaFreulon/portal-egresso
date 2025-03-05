package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.repo.CoordenadorRepo;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import com.ufma.PortalEgresso.model.repo.DepoimentoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import com.ufma.PortalEgresso.model.entity.ENUMs.Status;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CoordenadorServiceTest {

    @Autowired
    CoordenadorService service;

    @Autowired
    CoordenadorRepo repo;

    @Autowired
    EgressoRepo egressoRepo;

    @Autowired
    DepoimentoRepo depoimentoRepo;

    @Autowired
    private DepoimentoService depoimentoService;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CoordenadorRepo coordenadorRepo;
    @Autowired
    private CursoRepo cursoRepo;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemLogin() {
        Coordenador coordenador = new Coordenador();
        coordenador.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(coordenador), "O login do coordenador deve estar preenchido");
        Assertions.assertEquals("O login do coordenador deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemSenha() {
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("login teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(coordenador), "A senha do coordenador deve estar preenchida");
        Assertions.assertEquals("A senha do coordenador deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarLoginJaCadastrado() {
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("coordenador1");
        coordenador.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(coordenador), "Já existe um coordenador com esse login. Por favor, utilize um login diferente");
        Assertions.assertEquals("Já existe um coordenador com esse login. Por favor, utilize um login diferente", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemLogin() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        coordenador.setLogin(null);
        coordenador.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(coordenador), "O login do coordenador deve estar preenchido");
        Assertions.assertEquals("O login do coordenador deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemSenha() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        coordenador.setSenha(null);
        coordenador.setLogin("login teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(coordenador), "A senha do coordenador deve estar preenchida");
        Assertions.assertEquals("A senha do coordenador deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarLoginJaCadastrado() {
        Coordenador coordenadorSalvo = entityManager
                .createQuery("SELECT c FROM Coordenador c WHERE c.id = :id", Coordenador.class)
                .setParameter("id", UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4"))
                .getSingleResult();

        coordenadorSalvo.setLogin("coordenador2");
        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(coordenadorSalvo), "Já existe um coordenador com esse login. Por favor, utilize um login diferente");
        Assertions.assertEquals("Já existe um coordenador com esse login. Por favor, utilize um login diferente", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarCoordenadorNaoExistente() {
        Coordenador coordenador = new Coordenador();
        coordenador.setId_coordenador(UUID.randomUUID());
        coordenador.setLogin("login teste");
        coordenador.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(coordenador), "ID não encontrado");
        Assertions.assertEquals("ID não encontrado", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdVazio() {
        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(null), "ID inválido");
        Assertions.assertEquals("ID inválido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdInexistente() {
        UUID idInexistente = UUID.randomUUID();

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(idInexistente), "ID não encontrado");
        Assertions.assertEquals("ID não encontrado", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumCoordenador(){
        Query deleteCargo = entityManager.createQuery("Delete from Cargo");
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso");
        Query deleteEgresso = entityManager.createQuery("Delete from Egresso");
        Query deleteCurso = entityManager.createQuery("Delete from Curso");
        Query deleteCoordenador = entityManager.createQuery("Delete from Coordenador");
        Query deleteMensagem = entityManager.createQuery("Delete from Mensagem");
        Query deleteOportunidade = entityManager.createQuery("Delete from Oportunidade");
        Query deleteDiscussao = entityManager.createQuery("Delete from Discussao");

        deleteCargo.executeUpdate();
        deleteDepoimento.executeUpdate();
        deleteMensagem.executeUpdate();
        deleteOportunidade.executeUpdate();
        deleteDiscussao.executeUpdate();
        deleteCursoEgresso.executeUpdate();
        deleteEgresso.executeUpdate();
        deleteCurso.executeUpdate();
        deleteCoordenador.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroQuandoLoginIncorreto() {
        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class,
                () -> service.efetuarLogin("loginIncorreto", "senhaIncorreta"),
                "Erro de autenticação");
        Assertions.assertEquals("Erro de autenticação", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoCadastrarCursoComCoordenadorInvalido() {
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("loginInvalido");
        coordenador.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class,
                () -> service.cadastrarCurso(coordenador.getLogin(), "nome teste", "nivel teste"),
                "Login inválido");

        Assertions.assertEquals("Login inválido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarCadastrarEgressoSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.cadastrarEgresso(egresso), "Egresso inválido");
        Assertions.assertEquals("Egresso inválido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarCadastrarEgressoSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.cadastrarEgresso(egresso), "O Egresso inválido");
        Assertions.assertEquals("Egresso inválido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarCadastrarEgressoSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.cadastrarEgresso(egresso), "Egresso inválido");
        Assertions.assertEquals("Egresso inválido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAssociarCursoInvalidoAEgresso() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);

        Curso curso = new Curso();
        curso.setId_curso(UUID.randomUUID());

        Integer anoInicio = 2025;
        Integer anoFim = 2028;

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.associarCursoAEgresso(egresso, curso, anoInicio, anoFim), "Não foi possível associar curso a egresso");
        Assertions.assertEquals("Não foi possível associar curso a egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveHomologarDepoimento() {
        UUID id = UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c");
        Depoimento depoimento = depoimentoService.buscarPorId(id).get();

        Status status = Status.APROVADO;

        Depoimento homologado = service.homologarDepoimento(depoimento, status);

        Assertions.assertEquals(status, homologado.getStatus());
    }

    @Test
    @Transactional
    public void deveCadastrarCurso() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso cursoSalvo = service.cadastrarCurso(coordenador.getLogin(), "nome teste", "nivel teste");

        Assertions.assertEquals(coordenador, cursoSalvo.getCoordenador());
        Assertions.assertEquals("nome teste", cursoSalvo.getNome());
        Assertions.assertEquals("nivel teste", cursoSalvo.getNivel());
    }

    @Test
    @Transactional
    public void deveCadastrarEgresso() {
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("salvar@teste.com");
        egresso.setSenha("senha teste");

        Egresso egressoSalvo = service.cadastrarEgresso(egresso);

        Assertions.assertEquals(egresso, egressoSalvo);
    }

    @Test
    @Transactional
    public void deveAssociarCursoAEgresso() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        Curso curso = cursoRepo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);

        Curso cursoSalvo = service.associarCursoAEgresso(egresso, curso, 2022, null);

        Assertions.assertEquals(curso, cursoSalvo);
    }

    @Test
    @Transactional
    public void deveVerificarSalvarOCoordenador() {
        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("login teste");
        coordenador.setSenha("senha teste");

        Coordenador salvo = service.salvar(coordenador);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(coordenador.getLogin(), salvo.getLogin());
        Assertions.assertEquals(coordenador.getSenha(), salvo.getSenha());

    }

    @Test
    @Transactional
    public void deveVerificarAtualizarOCoordenador() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        coordenador.setLogin("login teste");
        coordenador.setSenha("senha teste");

        Coordenador salvo = service.salvar(coordenador);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(coordenador.getLogin(), salvo.getLogin());
        Assertions.assertEquals(coordenador.getSenha(), salvo.getSenha());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Coordenador encontrado = service.buscarPorId(coordenador.getId_coordenador()).orElse(null);

        Assertions.assertEquals(coordenador, encontrado);
    }

    @Test
    @Transactional
    public void deveListarTodosOsCoordenadores() {

        List<Coordenador> coordenadores = service.listarTodos();
        List<Coordenador> coordenadoresEsperados = entityManager
                .createQuery("SELECT c FROM Coordenador c", Coordenador.class)
                .getResultList();

        Assertions.assertEquals(coordenadores, coordenadoresEsperados);
    }

    @Test
    @Transactional
    public void deveDeletarCoordenador() {
        Coordenador coordenador = repo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        service.deletar(coordenador.getId_coordenador());
        Assertions.assertFalse(repo.existsById(coordenador.getId_coordenador()));
    }

    @Test
    @Transactional
    public void deveEfetuarLogin() {
        boolean resultado = service.efetuarLogin("coordenador1", "senha1");
        Assertions.assertTrue(resultado);
    }

}
