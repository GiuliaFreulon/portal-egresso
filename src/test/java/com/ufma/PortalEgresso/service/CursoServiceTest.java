package com.ufma.PortalEgresso.service;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Coordenador;
import com.ufma.PortalEgresso.model.entity.Curso;
import jakarta.persistence.Query;
import com.ufma.PortalEgresso.model.repo.CoordenadorRepo;
import com.ufma.PortalEgresso.model.repo.CursoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class CursoServiceTest {

    @Autowired
    private CursoService service;

    @Autowired
    private CoordenadorRepo coordenadorRepo;

    @Autowired
    private CursoRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemNome() {
        Coordenador coordenador = coordenadorRepo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso curso = new Curso();
        curso.setCoordenador(coordenador);
        curso.setNivel("nivel teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O nome do curso deve estar preenchido");
        Assertions.assertEquals("O nome do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemNivel() {
        Coordenador coordenador = coordenadorRepo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso curso = new Curso();
        curso.setCoordenador(coordenador);
        curso.setNome("nome teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O nível do curso deve estar preenchido");
        Assertions.assertEquals("O nível do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarSemCoordenador() {
        Curso curso = new Curso();
        curso.setNivel("nivel teste");
        curso.setNome("nome teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O coordenador do curso deve estar preenchido");
        Assertions.assertEquals("O coordenador do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemNome() {
        Coordenador coordenador = coordenadorRepo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        curso.setCoordenador(coordenador);
        curso.setNome(null);
        curso.setNivel("nivel teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O nome do curso deve estar preenchido");
        Assertions.assertEquals("O nome do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemNivel() {
        Coordenador coordenador = coordenadorRepo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        curso.setCoordenador(coordenador);
        curso.setNome("nome teste");
        curso.setNivel(null);

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O nível do curso deve estar preenchido");
        Assertions.assertEquals("O nível do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAtualizarSemCoordenador() {
        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        curso.setCoordenador(null);
        curso.setNivel("nivel teste");
        curso.setNome("nome teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.salvar(curso), "O coordenador do curso deve estar preenchido");
        Assertions.assertEquals("O coordenador do curso deve estar preenchido", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarCursoInexistente() {
        Curso curso = new Curso();
        curso.setId_curso(UUID.randomUUID());
        curso.setNome("nome teste");
        curso.setNivel("nivel teste");

        Exception exception = Assertions.assertThrows(RegraNegocioRunTime.class, () -> service.atualizar(curso), "ID não encontrado");
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
    public void deveGerarErroAoBuscarPorNomeInexistente() {
        String nome = "nome inexistente";

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.buscarPorNome(nome), "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumCurso(){
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso ");
        Query deleteCurso = entityManager.createQuery("Delete from Curso");

        deleteCursoEgresso.executeUpdate();
        deleteCurso.executeUpdate();

        Exception exception = Assertions.assertThrows(BuscaVaziaRunTime.class, () -> service.listarTodos() , "Nenhum resultado para a busca");
        Assertions.assertEquals("Nenhum resultado para a busca", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveVerificarSalvarCurso() {
        Coordenador coordenador = coordenadorRepo.findById(UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4")).orElse(null);
        assert coordenador != null;

        Curso curso = new Curso();
        curso.setCoordenador(coordenador);
        curso.setNome("nome teste");
        curso.setNivel("nivel teste");

        Curso salvo = service.salvar(curso);

        //Verificação
        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(curso.getNome(), salvo.getNome());
        Assertions.assertEquals(curso.getNivel(), salvo.getNivel());
        Assertions.assertEquals(curso.getCoordenador(), salvo.getCoordenador());
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarCurso() {

        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        curso.setNome("nome teste atualização");
        curso.setNivel("nivel teste atualização");

        Curso atualizado = service.atualizar(curso);

        //Verificação
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals(curso.getNome(), atualizado.getNome());
        Assertions.assertEquals(curso.getNivel(), atualizado.getNivel());
        Assertions.assertEquals(curso.getCoordenador(), atualizado.getCoordenador());
    }


    @Test
    @Transactional
    public void deveBuscarPorNome() {
        String nome = "curso 1";

        List<Curso> cursos = service.buscarPorNome(nome);
        List<Curso> cursosEsperados = entityManager
                .createQuery("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCurso, '%'))", Curso.class)
                .setParameter("nomeCurso", nome)
                .getResultList();

        Assertions.assertEquals(cursos, cursosEsperados);
    }



    @Test
    @Transactional
    public void deveBuscarPorIdExistente() {
        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        Curso encontrado = service.buscarPorId(curso.getId_curso()).orElse(null);

        Assertions.assertEquals(curso, encontrado);
    }

    @Test
    @Transactional
    public void deveListarTodosOsCursos() {

        List<Curso> cursos = service.listarTodos();
        List<Curso> cursosEsperados = entityManager
                .createQuery("SELECT c FROM Curso c", Curso.class)
                .getResultList();

        Assertions.assertEquals(cursos, cursosEsperados);
    }

    @Test
    @Transactional
    public void deveDeletarCurso() {
        Curso curso = repo.findById(UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce")).orElse(null);
        assert curso != null;

        service.deletar(curso.getId_curso());
        Assertions.assertFalse(repo.existsById(curso.getId_curso()));
    }

}
