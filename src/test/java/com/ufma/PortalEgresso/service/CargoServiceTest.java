package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.CargoRepo;
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
public class CargoServiceTest {
    @Autowired
    private CargoService service;

    @Autowired
    private CargoRepo repo;

    @Autowired
    private EgressoRepo egressoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemDescricao() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = new Cargo();
        cargo.setEgresso(egresso);
        cargo.setLocal("local teste");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "A descrição do cargo deve estar preenchida");
        Assertions.assertEquals("A descrição do cargo deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemLocal() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = new Cargo();
        cargo.setEgresso(egresso);
        cargo.setDescricao("descricao teste");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O local do cargo deve estar preenchido");
        Assertions.assertEquals("O local do cargo deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemAnoInicio() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = new Cargo();
        cargo.setEgresso(egresso);
        cargo.setDescricao("descrição teste");
        cargo.setLocal("local teste");


        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O ano de início do cargo deve estar preenchido");
        Assertions.assertEquals("O ano de início do cargo deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemEgresso() {
        Cargo cargo = new Cargo();
        cargo.setLocal("local teste");
        cargo.setDescricao("descrição teste");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O cargo deve estar associado a um egresso");
        Assertions.assertEquals("O cargo deve estar associado a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemDescricao() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        cargo.setDescricao(null);
        cargo.setEgresso(egresso);
        cargo.setLocal("local teste atualização");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "A descrição do cargo deve estar preenchida");
        Assertions.assertEquals("A descrição do cargo deve estar preenchida", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemLocal() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        cargo.setDescricao("descricao teste atualização");
        cargo.setEgresso(egresso);
        cargo.setLocal(null);
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O local do cargo deve estar preenchido");
        Assertions.assertEquals("O local do cargo deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemAnoInicio() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        cargo.setDescricao("descrição teste atualização");
        cargo.setEgresso(egresso);
        cargo.setLocal("local teste atualização");
        cargo.setAnoInicio(null);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O ano de início do cargo deve estar preenchido");
        Assertions.assertEquals("O ano de início do cargo deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemEgresso() {
        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        cargo.setDescricao("descrição teste atualização");
        cargo.setEgresso(null);
        cargo.setLocal("local teste atualização");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(cargo), "O cargo deve estar associado a um egresso");
        Assertions.assertEquals("O cargo deve estar associado a um egresso", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarCargoInexistente() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = new Cargo();
        cargo.setId_cargo(UUID.randomUUID());
        cargo.setDescricao("descrição teste atualização");
        cargo.setEgresso(egresso);
        cargo.setLocal("local teste atualização");
        cargo.setAnoInicio(2020);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(cargo), "ID não encontrado");
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
    public void deveGerarErroAoNaoEncontrarNenhumCargo(){
        Query deleteCargo = entityManager.createQuery("Delete from Cargo");

        deleteCargo.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarCargo() {
        Egresso egresso = egressoRepo.findById(UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531")).orElse(null);
        assert egresso != null;

        Cargo cargo = new Cargo();
        cargo.setEgresso(egresso);
        cargo.setLocal("local teste");
        cargo.setDescricao("descricao teste");
        cargo.setAnoInicio(2020);

        Cargo salvo = service.salvar(cargo);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(cargo.getLocal(), salvo.getLocal());
        Assertions.assertEquals(cargo.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(cargo.getAnoInicio(), salvo.getAnoInicio());
        Assertions.assertEquals(cargo.getEgresso(), salvo.getEgresso());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarCargo() {
        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        cargo.setLocal("local teste atualização");
        cargo.setDescricao("descricao teste atualização");
        cargo.setAnoInicio(2020);

        Cargo atualizado = service.atualizar(cargo);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(cargo.getLocal(), atualizado.getLocal());
        Assertions.assertEquals(cargo.getDescricao(), atualizado.getDescricao());
        Assertions.assertEquals(cargo.getAnoInicio(), atualizado.getAnoInicio());
        Assertions.assertEquals(cargo.getEgresso(), atualizado.getEgresso());
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        Cargo encontrado = service.buscarPorId(cargo.getId_cargo()).orElse(null);

        Assertions.assertEquals(cargo, encontrado);
    }

    @Test
    @Transactional
    public void deveListarTodosOsCargos() {

        List<Cargo> cargos = service.listarTodos();
        List<Cargo> cargosEsperados = entityManager
                .createQuery("SELECT c FROM Cargo c", Cargo.class)
                .getResultList();

        Assertions.assertEquals(cargos, cargosEsperados);
    }

    @Test
    @Transactional
    public void deveDeletarCargo() {
        Cargo cargo = repo.findById(UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052")).orElse(null);
        assert cargo != null;

        service.deletar(cargo.getId_cargo());
        Assertions.assertFalse(repo.existsById(cargo.getId_cargo()));
    }

}
