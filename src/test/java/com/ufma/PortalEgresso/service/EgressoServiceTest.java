package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.repo.CargoRepo;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
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
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EgressoServiceTest {

    @Autowired
    private EgressoService service;

    @Autowired
    private EgressoRepo repo;

    @Autowired
    private CursoRepo cursoRepo;

    @Autowired
    private CargoRepo cargoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O nome do egresso deve estar preenchido");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail do egresso deve estar preenchido");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "A senha do egresso deve estar preenchida");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarEmailJaCadastrado() {

        Egresso egresso = new Egresso();
        egresso.setNome("teste nome1");
        egresso.setDescricao("Descricao teste1");
        egresso.setEmail("egresso1@email.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
    }

//    @Test
//    public void deveGerarErroAoTentarSalvarEmailInvalido(){
//        Egresso egresso = new Egresso();
//        egresso.setNome("teste nome");
//        egresso.setDescricao("Descricao teste");
//        egresso.setEmail("teste");
//
//        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "E-mail inválido. Verifique o formato");
//    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "O nome do egresso deve estar preenchido");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "O e-mail do egresso deve estar preenchido");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "A senha do egresso deve estar preenchida");
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarEmailJaCadastrado() {
        Egresso egressoSalvo = entityManager
                .createQuery("SELECT e FROM Egresso e WHERE e.id_egresso = :id", Egresso.class)
                .setParameter("id", UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531"))
                .getSingleResult();

        egressoSalvo.setEmail("egresso2@email.com");
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egressoSalvo), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
    }

//    @Test
//    public void deveGerarErroAoTentarAtualizarEmailInvalido(){
//        Egresso egresso = new Egresso();
//        egresso.setNome("teste nome");
//        egresso.setDescricao("Descricao teste");
//        egresso.setEmail("teste");
//
//        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "E-mail inválido. Verifique o formato");
//    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarUmEgressoNaoExistente() {
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "Nenhum resultado para a busca");
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdVazio() {
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(null), "ID inválido");
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdInexistente() {
        UUID idInexistente = UUID.randomUUID();

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(idInexistente), "ID não encontrado");
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorCursoInexistente() {
        Curso curso = new Curso();

        Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorCurso(curso), "Nenhum resultado para a busca");
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorCargoInexistente() {
        Cargo cargo = new Cargo();
        Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorCargo(cargo), "Nenhum resultado para a busca");
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumEgresso(){
        Query deleteCargo = entityManager.createQuery("Delete from Cargo");
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso");
        Query deleteEgresso = entityManager.createQuery("Delete from Egresso");

        deleteCargo.executeUpdate();
        deleteDepoimento.executeUpdate();
        deleteCursoEgresso.executeUpdate();
        deleteEgresso.executeUpdate();

        Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
    }

    @Test
    @Transactional
    public void deveGerarErroQuandoLoginIncorreto() {
        Assertions.assertThrows(RegraNegocioRunTime.class,
                () -> service.efetuarLogin("loginIncorreto@example.com", "senhaIncorreta"),
                "Erro de autenticação");
    }

    @Test
    @Transactional
    public void deveVerificarSalvarOEgresso() {
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("salvar@teste.com");
        egresso.setSenha("senha teste");

        Egresso salvo = service.salvar(egresso);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(egresso.getNome(), salvo.getNome());
        Assertions.assertEquals(egresso.getEmail(), salvo.getEmail());
        Assertions.assertEquals(egresso.getSenha(), salvo.getSenha());
        Assertions.assertEquals(egresso.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(egresso.getFoto(), salvo.getFoto());
        Assertions.assertEquals(egresso.getLinkedin(), salvo.getLinkedin());
        Assertions.assertEquals(egresso.getInstagram(), salvo.getInstagram());
        Assertions.assertEquals(egresso.getCurriculo(), salvo.getCurriculo());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarOEgresso() {

        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        egresso.setNome("nome teste atualização");
        egresso.setDescricao("Descricao teste atualização");
        egresso.setEmail("atualização@teste.com");
        egresso.setSenha("senha teste atualização");

        Egresso atualizado = service.atualizar(egresso);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(egresso.getNome(), atualizado.getNome());
        Assertions.assertEquals(egresso.getEmail(), atualizado.getEmail());
        Assertions.assertEquals(egresso.getSenha(), atualizado.getSenha());
        Assertions.assertEquals(egresso.getDescricao(), atualizado.getDescricao());
        Assertions.assertEquals(egresso.getFoto(), atualizado.getFoto());
        Assertions.assertEquals(egresso.getLinkedin(), atualizado.getLinkedin());
        Assertions.assertEquals(egresso.getInstagram(), atualizado.getInstagram());
        Assertions.assertEquals(egresso.getCurriculo(), atualizado.getCurriculo());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Egresso encontrado = service.buscarPorId(egresso.getId_egresso())
                .orElseThrow(() -> new RegraNegocioRunTime("ID não encontrado"));

        Assertions.assertEquals(egresso, encontrado);
    }

    @Test
    @Transactional
    public void deveBuscarPorCursoExistente() {
        Curso curso = cursoRepo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        List<Egresso> egressos = service.buscarPorCurso(curso);
        List<Egresso> egressosEsperados = entityManager
                .createQuery("SELECT e FROM Egresso e JOIN CursoEgresso ce ON e = ce.egresso WHERE ce.curso.id_curso = :id", Egresso.class)
                .setParameter("id", UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce"))
                .getResultList();

        Assertions.assertEquals(egressos, egressosEsperados);
    }

    @Test
    @Transactional
    public void deveBuscarPorCargoExistente() {

        Cargo cargo = cargoRepo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        List<Egresso> egressos = service.buscarPorCargo(cargo);
        List<Egresso> egressosEsperados = entityManager
                .createQuery("SELECT e FROM Egresso e JOIN Cargo c ON c.egresso = e WHERE c.id = :id", Egresso.class)
                .setParameter("id", UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052"))
                .getResultList();

        Assertions.assertEquals(egressos, egressosEsperados);
    }

    @Test
    @Transactional
    public void deveListarTodosOsEgressos() {

        List<Egresso> egressos = service.listarTodos();
        List<Egresso> egressosEsperados = entityManager
                .createQuery("SELECT e FROM Egresso e", Egresso.class)
                .getResultList();

        Assertions.assertEquals(egressos, egressosEsperados);
    }

    @Test
    @Transactional
    public void deveDeletarEgresso() {
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        service.deletar(egresso.getId_egresso());
        Assertions.assertFalse(repo.existsById(egresso.getId_egresso()));
    }

    @Test
    @Transactional
    public void deveEfetuarLogin() {
        boolean resultado = service.efetuarLogin("egresso1@email.com", "senha1");
        Assertions.assertTrue(resultado);
    }
}