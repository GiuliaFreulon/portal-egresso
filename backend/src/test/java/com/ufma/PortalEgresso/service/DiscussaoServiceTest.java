package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Discussao;
import com.ufma.PortalEgresso.model.entity.Egresso;
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

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DiscussaoServiceTest {
    @Autowired
    private DiscussaoService service;

    @Autowired
    private DiscussaoRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEgresso() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Discussao discussao = Discussao.builder()
                .titulo("titulo teste")
                .build();

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(discussao), "A discussão deve estar associada a um egresso");
        Assertions.assertEquals("A discussão deve estar associada a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemEgresso() {
        Discussao discussao = repo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).orElse(null);
        assert discussao != null;

        discussao.setEgresso(null);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(discussao), "A discussão deve estar associada a um egresso");
        Assertions.assertEquals("A discussão deve estar associada a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarDiscussaoInexistente() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Discussao discussao = Discussao.builder()
                .titulo("titulo teste")
                .build();
        discussao.setId_discussao(UUID.randomUUID());
        discussao.setEgresso(egresso);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(discussao), "ID não encontrado");
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
    public void deveGerarErroAoNaoEncontrarNenhumaDiscussao(){
        Query deleteDiscussao = entityManager.createQuery("Delete from Discussao");
        Query deleteMensagem = entityManager.createQuery("Delete from Mensagem");

        deleteMensagem.executeUpdate();
        deleteDiscussao.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarDiscussao() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Discussao discussao = Discussao.builder()
                .egresso(egresso)
                .titulo("titulo teste")
                .build();

        Discussao salvo = service.salvar(discussao);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(discussao.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(discussao.getTitulo(), salvo.getTitulo());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarDiscussao() {
        Discussao discussao = repo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).orElse(null);
        assert discussao != null;

        discussao.setTitulo("titulo teste atualização");

        Discussao atualizado = service.atualizar(discussao);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(discussao.getEgresso(), atualizado.getEgresso());
        Assertions.assertEquals(discussao.getTitulo(), atualizado.getTitulo());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Discussao discussao = repo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).orElse(null);
        assert discussao != null;

        Discussao encontrado = service.buscarPorId(discussao.getId_discussao()).orElse(null);

        Assertions.assertEquals(discussao, encontrado);
    }

    @Test
    @Transactional
    public void deveListarTodasAsDiscussoes() {

        List<Discussao> discussoes = service.listarTodos();
        List<Discussao> discussoesEsperadas = entityManager
                .createQuery("SELECT d FROM Discussao d", Discussao.class)
                .getResultList();

        Assertions.assertEquals(discussoes, discussoesEsperadas);
    }

    @Test
    @Transactional
    public void deveDeletarDiscussao() {
        Discussao discussao = repo.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).orElse(null);
        assert discussao != null;

        service.deletar(discussao.getId_discussao());
        Assertions.assertFalse(repo.existsById(discussao.getId_discussao()));
    }

}
