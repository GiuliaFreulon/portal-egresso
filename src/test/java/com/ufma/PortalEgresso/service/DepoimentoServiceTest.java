package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.DepoimentoRepo;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DepoimentoServiceTest {
    @Autowired
    private DepoimentoService service;

    @Autowired
    private DepoimentoRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEgresso() {
        Depoimento depoimento = new Depoimento();

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(depoimento), "O depoimento deve estar associado a um egresso");
        Assertions.assertEquals("O depoimento deve estar associado a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemEgresso() {
        Depoimento depoimento = repo.findById(UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c")).orElse(null);
        assert depoimento != null;

        depoimento.setEgresso(null);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(depoimento), "O depoimento deve estar associado a um egresso");
        Assertions.assertEquals("O depoimento deve estar associado a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarDepoimentoInexistente() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Depoimento depoimento = new Depoimento();
        depoimento.setId_depoimento(UUID.randomUUID());
        depoimento.setEgresso(egresso);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(depoimento), "ID não encontrado");
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
    public void deveGerarErroAoNaoEncontrarNenhumDepoimentoPorAno(){
        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorAno(2023), "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumDepoimentoRecente(){
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");

        deleteDepoimento.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumDepoimento(){
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");

        deleteDepoimento.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarDepoimento() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Depoimento depoimento = new Depoimento();
        depoimento.setEgresso(egresso);
        depoimento.setTexto("texto teste");
        depoimento.setData(LocalDate.of(2001, 9, 11));
        Depoimento salvo = service.salvar(depoimento);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimento.getTexto(), salvo.getTexto());
        Assertions.assertEquals(depoimento.getData(), salvo.getData());
        Assertions.assertEquals(depoimento.getEgresso(), salvo.getEgresso());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarDepoimento() {
        Depoimento depoimento = repo.findById(UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c")).orElse(null);
        assert depoimento != null;



        depoimento.setTexto("texto teste atualização");
        depoimento.setData(LocalDate.of(2024, 1, 1));

        Depoimento atualizado = service.atualizar(depoimento);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(depoimento.getTexto(), atualizado.getTexto());
        Assertions.assertEquals(depoimento.getData(), atualizado.getData());
        Assertions.assertEquals(depoimento.getEgresso(), atualizado.getEgresso());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Depoimento depoimento = repo.findById(UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c")).orElse(null);
        assert depoimento != null;

        Depoimento encontrado = service.buscarPorId(depoimento.getId_depoimento()).orElse(null);

        Assertions.assertEquals(depoimento, encontrado);
    }

    @Test
    @Transactional
    public void deveBuscarDepoimentoPorNomeDoCurso() {
        String nome = "Curso 1";

        List<Depoimento> cursos = service.buscarPorNomeCurso(nome);
        List<Depoimento> cursosEsperados = entityManager
                .createQuery("SELECT d FROM Depoimento d JOIN Egresso e ON d.egresso = e " +
                        "JOIN CursoEgresso ce ON e = ce.egresso " +
                        "WHERE LOWER(ce.curso.nome) LIKE LOWER(CONCAT('%', :cursoNome, '%'))", Depoimento.class)
                .setParameter("cursoNome", nome)
                .getResultList();

        Assertions.assertEquals(cursos, cursosEsperados);
    }

    @Test
    @Transactional
    public void deveBuscarPorAnoExistente() {

        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 12, 31);

        List<Depoimento> depoimentos = service.buscarPorAno(2024);
        List<Depoimento> depoimentosEsperados = entityManager
                .createQuery("SELECT d FROM Depoimento d WHERE d.data >= :dataInicio AND d.data <= :dataFim", Depoimento.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim)
                .getResultList();

        Assertions.assertEquals(depoimentos, depoimentosEsperados);
    }

    @Test
    @Transactional
    public void deveBuscarPorRecentes() {

        LocalDate trintaDiasAtras = LocalDate.now().minusDays(30);

        List<Depoimento> depoimentos = service.buscarRecentes();
        List<Depoimento> depoimentosEsperados = entityManager
                .createQuery("SELECT d FROM Depoimento d WHERE d.data >= :dataInicio ORDER BY d.data DESC", Depoimento.class)
                .setParameter("dataInicio", trintaDiasAtras)
                .getResultList();

        Assertions.assertEquals(depoimentos, depoimentosEsperados);
    }

    @Test
    @Transactional
    public void deveListarTodosOsDepoimentos() {

        List<Depoimento> depoimentos = service.listarTodos();
        List<Depoimento> depoimentosEsperados = entityManager
                .createQuery("SELECT c FROM Depoimento c", Depoimento.class)
                .getResultList();

        Assertions.assertEquals(depoimentos, depoimentosEsperados);
    }

    @Test
    @Transactional
    public void deveDeletarDepoimento() {
        Depoimento depoimento = repo.findById(UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c")).orElse(null);
        assert depoimento != null;

        service.deletar(depoimento.getId_depoimento());
        Assertions.assertFalse(repo.existsById(depoimento.getId_depoimento()));
    }

}
