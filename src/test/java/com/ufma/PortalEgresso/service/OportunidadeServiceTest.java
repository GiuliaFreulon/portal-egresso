package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.entity.Oportunidade;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import com.ufma.PortalEgresso.model.repo.OportunidadeRepo;
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
public class OportunidadeServiceTest {
    @Autowired
    private OportunidadeService service;

    @Autowired
    private OportunidadeRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEgresso() {
        Oportunidade oportunidade = new Oportunidade();

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(oportunidade), "A oportunidade deve estar associada a um egresso");
        Assertions.assertEquals("A oportunidade deve estar associada a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemEgresso() {
        Oportunidade oportunidade = repo.findById(UUID.fromString("f97ab281-a671-4e57-978a-078696b28e49")).orElse(null);
        assert oportunidade != null;

        oportunidade.setEgresso(null);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(oportunidade), "A discussao deve estar associada a um egresso");
        Assertions.assertEquals("A discussao deve estar associada a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarOportunidadeInexistente() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Oportunidade oportunidade = new Oportunidade();
        oportunidade.setId_oportunidade(UUID.randomUUID());
        oportunidade.setEgresso(egresso);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(oportunidade), "ID não encontrado");
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
    public void deveGerarErroAoNaoEncontrarNenhumaOportunidade(){
        Query deleteOportunidade = entityManager.createQuery("Delete from Oportunidade");

        deleteOportunidade.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarOportunidade() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Oportunidade oportunidade = Oportunidade.builder()
                .egresso(egresso)
                .titulo("titulo teste")
                .descricao("descrição teste")
                .build();

        Oportunidade salvo = service.salvar(oportunidade);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(oportunidade.getEgresso(), salvo.getEgresso());
        Assertions.assertEquals(oportunidade.getTitulo(), salvo.getTitulo());
        Assertions.assertEquals(oportunidade.getDescricao(), salvo.getDescricao());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarOportunidade() {
        Oportunidade oportunidade = repo.findById(UUID.fromString("f97ab281-a671-4e57-978a-078696b28e49")).orElse(null);
        assert oportunidade != null;

        oportunidade.setTitulo("titulo teste atualização");

        Oportunidade atualizado = service.atualizar(oportunidade);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(oportunidade.getEgresso(), atualizado.getEgresso());
        Assertions.assertEquals(oportunidade.getTitulo(), atualizado.getTitulo());
        Assertions.assertEquals(oportunidade.getDescricao(), atualizado.getDescricao());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Oportunidade oportunidade = repo.findById(UUID.fromString("f97ab281-a671-4e57-978a-078696b28e49")).orElse(null);
        assert oportunidade != null;

        Oportunidade encontrado = service.buscarPorId(oportunidade.getId_oportunidade()).orElse(null);

        Assertions.assertEquals(oportunidade, encontrado);
    }

    @Test
    @Transactional
    public void deveListarTodasAsOportunidades() {

        List<Oportunidade> oportunidades = service.listarTodos();
        List<Oportunidade> oportunidadesEsperadas = entityManager
                .createQuery("SELECT o FROM Oportunidade o", Oportunidade.class)
                .getResultList();

        Assertions.assertEquals(oportunidades, oportunidadesEsperadas);
    }

    @Test
    @Transactional
    public void deveDeletarOportunidade() {
        Oportunidade oportunidade = repo.findById(UUID.fromString("f97ab281-a671-4e57-978a-078696b28e49")).orElse(null);
        assert oportunidade != null;

        service.deletar(oportunidade.getId_oportunidade());
        Assertions.assertFalse(repo.existsById(oportunidade.getId_oportunidade()));
    }

}
