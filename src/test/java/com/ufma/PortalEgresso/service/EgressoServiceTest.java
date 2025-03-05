package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.repo.CargoRepo;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import com.ufma.PortalEgresso.model.repo.DiscussaoRepo;
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

import java.time.LocalDateTime;
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

    @Autowired
    private DiscussaoRepo discussaoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O nome do egresso deve estar preenchido");
        Assertions.assertEquals("O nome do egresso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail do egresso deve estar preenchido");
        Assertions.assertEquals("O e-mail do egresso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "A senha do egresso deve estar preenchida");
        Assertions.assertEquals("A senha do egresso deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarEmailJaCadastrado() {

        Egresso egresso = new Egresso();
        egresso.setNome("teste nome1");
        egresso.setDescricao("Descricao teste1");
        egresso.setEmail("egresso1@email.com");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
        Assertions.assertEquals("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarEmailJaCadastrado() {
        Egresso egressoSalvo = entityManager
                .createQuery("SELECT e FROM Egresso e WHERE e.id_egresso = :id", Egresso.class)
                .setParameter("id", UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531"))
                .getSingleResult();

        egressoSalvo.setEmail("egresso2@email.com");
        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egressoSalvo), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
        Assertions.assertEquals("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarUmEgressoNaoExistente() {
        Egresso egresso = new Egresso();
        egresso.setId_egresso(UUID.randomUUID());
        egresso.setNome("nome teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "ID não encontrado");
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
    public void deveGerarErroAoBuscarPorNomeInexistente() {
        String nome = "nome inexistente";

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorNome(nome), "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
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
    public void deveGerarErroAoBuscarPorCursoInexistente() {
        Curso curso = new Curso();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorCurso(curso), "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorCargoInexistente() {
        Cargo cargo = new Cargo();
        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorCargo(cargo), "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumEgresso(){
        Query deleteCurso = entityManager.createQuery("Delete from Curso");
        Query deleteCoordenador = entityManager.createQuery("Delete from Coordenador");
        Query deleteCargo = entityManager.createQuery("Delete from Cargo");
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso");
        Query deleteEgresso = entityManager.createQuery("Delete from Egresso");
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
                () -> service.efetuarLogin("loginIncorreto@example.com", "senhaIncorreta"),
                "Erro de autenticação");
        Assertions.assertEquals("Erro de autenticação", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarEnviarDepoimento(){
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Depoimento depoimento = Depoimento.builder()
                .egresso(egresso)
                .texto("texto teste")
                .build();
        Depoimento enviado = service.enviarDepoimento(depoimento);

        //Verificação
        Assertions.assertNotNull(enviado);
        Assertions.assertEquals(depoimento.getEgresso(), enviado.getEgresso());
        Assertions.assertEquals(depoimento.getTexto(), enviado.getTexto());
    }

    @Test
    @Transactional
    public void deveVerificarEnviarOportunidade(){
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Oportunidade oportunidade = Oportunidade.builder()
                .egresso(egresso)
                .descricao("descrição teste")
                .titulo("titulo teste")
                .build();

        Oportunidade enviado = service.enviarOportunidade(oportunidade);

        //Verificação
        Assertions.assertNotNull(enviado);
        Assertions.assertEquals(oportunidade.getEgresso(), enviado.getEgresso());
        Assertions.assertEquals(oportunidade.getDescricao(), enviado.getDescricao());
        Assertions.assertEquals(oportunidade.getTitulo(), enviado.getTitulo());
    }

    @Test
    @Transactional
    public void deveVerificarCriarDiscussao(){
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Discussao discussao = Discussao.builder()
                .egresso(egresso)
                .titulo("titulo teste")
                .build();

        Discussao enviado = service.criarDiscussao(discussao);

        //Verificação
        Assertions.assertNotNull(enviado);
        Assertions.assertEquals(discussao.getEgresso(), enviado.getEgresso());
        Assertions.assertEquals(discussao.getTitulo(), enviado.getTitulo());
    }

    @Test
    @Transactional
    public void deveVerificarEnviarMensagem(){
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        Discussao discussao = discussaoRepo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).get();
        assert egresso != null;

        Mensagem mensagem = Mensagem.builder()
                .egresso(egresso)
                .discussao(discussao)
                .texto("texto teste")
                .dataEnvio(LocalDateTime.now())
                .build();

        Mensagem enviado = service.enviarMensagem(mensagem);

        //Verificação
        Assertions.assertNotNull(enviado);
        Assertions.assertEquals(mensagem.getEgresso(), enviado.getEgresso());
        Assertions.assertEquals(mensagem.getDiscussao(), enviado.getDiscussao());
        Assertions.assertEquals(mensagem.getTexto(), enviado.getTexto());
        Assertions.assertEquals(mensagem.getDataEnvio(), enviado.getDataEnvio());
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
        Assertions.assertEquals(egresso.getGithub(), salvo.getGithub());
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
        Assertions.assertEquals(egresso.getGithub(), atualizado.getGithub());
        Assertions.assertEquals(egresso.getCurriculo(), atualizado.getCurriculo());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Egresso egresso = repo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Egresso encontrado = service.buscarPorId(egresso.getId_egresso()).get();

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
    public void deveBuscarPorNome() {
        String nome = "egresso 1";

        List<Egresso> egressos = service.buscarPorNome(nome);
        List<Egresso> egressosEsperados = entityManager
                .createQuery("SELECT c FROM Egresso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeEgresso, '%'))", Egresso.class)
                .setParameter("nomeEgresso", nome)
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