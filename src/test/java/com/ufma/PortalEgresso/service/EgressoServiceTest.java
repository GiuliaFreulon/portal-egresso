package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Coordenador;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.model.entity.CursoEgresso;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.repo.EgressoRepo;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EgressoServiceTest {

    @Autowired
    CursoService cursoService;

    @Autowired
    CursoEgressoService cursoEgressoService;

    @Autowired
    CoordenadorService coordenadorService;

    @Autowired
    EgressoService service;

    @Autowired
    EgressoRepo repo;

    @Test
    public void deveGerarErroAoTentarSalvarSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O nome do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "O e-mail do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarSalvarSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso), "A senha do egresso deve estar preenchida");
    }

    @Test
    public void deveGerarErroAoTentarSalvarEmailJaCadastrado() {
        Egresso egresso1 = new Egresso();
        egresso1.setNome("teste nome1");
        egresso1.setDescricao("Descricao teste1");
        egresso1.setEmail("teste@teste.com");
        egresso1.setSenha("senha teste");

        Egresso egresso2 = new Egresso();
        egresso2.setNome("teste nome2");
        egresso2.setDescricao("Descricao teste2");
        egresso2.setEmail("teste@teste.com");
        egresso2.setSenha("senha teste");

        service.salvar(egresso1);
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(egresso2), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
    }

    @Test
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
    public void deveGerarErroAoTentarAtualizarSemNome() {
        Egresso egresso = new Egresso();
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "O nome do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarAtualizarSemEmail() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "O e-mail do egresso deve estar preenchido");
    }

    @Test
    public void deveGerarErroAoTentarAtualizarSemSenha() {
        Egresso egresso = new Egresso();
        egresso.setNome("teste nome");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "A senha do egresso deve estar preenchida");
    }

    @Test
    public void deveGerarErroAoTentarAtualizarEmailJaCadastrado() {
        Egresso egresso1 = new Egresso();
        egresso1.setNome("teste nome1");
        egresso1.setDescricao("Descricao teste1");
        egresso1.setEmail("teste@teste1.com");
        egresso1.setSenha("senha teste");

        Egresso egresso2 = new Egresso();
        egresso2.setNome("teste nome2");
        egresso2.setDescricao("Descricao teste2");
        egresso2.setEmail("teste@teste2.com");
        egresso2.setSenha("senha teste");

        service.salvar(egresso1);
        service.salvar(egresso2);

        egresso2.setEmail("teste@teste1.com");
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso2), "O e-mail já está cadastrado. Por favor, utilize um e-mail diferente");
    }

    @Test
    public void deveVerificarAtualizarOEgresso() {
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Egresso salvo = service.salvar(egresso);

        egresso.setNome("nome teste atualização");
        egresso.setDescricao("Descricao teste atualização");
        egresso.setEmail("atualização@teste.com");
        egresso.setSenha("senha teste atualização");

        Egresso atualizado = service.atualizar(egresso);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(salvo.getNome(), atualizado.getNome());
        Assertions.assertEquals(salvo.getEmail(), atualizado.getEmail());
        Assertions.assertEquals(salvo.getSenha(), atualizado.getSenha());
        Assertions.assertEquals(salvo.getDescricao(), atualizado.getDescricao());
        Assertions.assertEquals(salvo.getFoto(), atualizado.getFoto());
        Assertions.assertEquals(salvo.getLinkedin(), atualizado.getLinkedin());
        Assertions.assertEquals(salvo.getInstagram(), atualizado.getInstagram());
        Assertions.assertEquals(salvo.getCurriculo(), atualizado.getCurriculo());
    }

    @Test
    public void deveGerarErroAoAtualizarUmEgressoNaoExistente() {
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setEmail("teste@teste.com");
        egresso.setSenha("senha teste");

        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(egresso), "Nenhum resultado para a busca");
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
    public void deveGerarErroAoBuscarPorIdVazio() {
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(null), "ID inválido");
    }

    @Test
    public void deveGerarErroAoBuscarPorIdInexistente() {
        Egresso egresso = new Egresso();
        Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.buscarPorId(egresso.getId_egresso()), "ID não encontrado");
    }

    @Test
    public void deveGerarErroAoBuscarPorCursoInexistente() {
        Curso curso2 = new Curso();

        Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorCurso(curso2), "Nenhum resultado para a busca");
    }

    @Transactional
    @Test
    public void deveBuscarPorCursoExistente() {
        List<Egresso> egressosEsperados = new ArrayList<>();
        Egresso egresso = new Egresso();
        egresso.setNome("nome teste");
        egresso.setDescricao("Descricao teste");
        egresso.setEmail("salvar@teste.com");
        egresso.setSenha("senha teste");
        egressosEsperados.add(egresso);
        service.salvar(egresso);

        Coordenador coordenador = new Coordenador();
        coordenador.setLogin("teste");
        coordenador.setSenha("teste");
        coordenador.setTipo("teste");
        coordenadorService.salvar(coordenador);

        Curso curso = new Curso();
        curso.setNivel("testeNivel");
        curso.setNome("teste");
        curso.setCoordenador(coordenador);
        cursoService.salvar(curso);

        Set<CursoEgresso> cursos = new HashSet<>();
        CursoEgresso cursoEgresso = new CursoEgresso();
        cursoEgresso.setEgresso(egresso);
        cursoEgresso.setCurso(curso);
        cursoEgresso.setAnoInicio(2024);
        cursoEgressoService.salvar(cursoEgresso);

        cursos.add(cursoEgresso);

        egresso.setCursos(cursos);

        List<Egresso> egressos = service.buscarPorCurso(cursoEgresso.getCurso());


        Assertions.assertEquals(egressos, egressosEsperados);
    }
}