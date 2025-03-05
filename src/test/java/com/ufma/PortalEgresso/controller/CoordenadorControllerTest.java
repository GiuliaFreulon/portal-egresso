package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.*;
import com.ufma.PortalEgresso.model.entity.ENUMs.Status;
import com.ufma.PortalEgresso.model.repo.DepoimentoRepo;
import com.ufma.PortalEgresso.service.DepoimentoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CoordenadorControllerTest {
    final static String API = "/api/coordenador";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DepoimentoRepo depoimentoRepo;
    @Autowired
    private DepoimentoService depoimentoService;

    @Test
    @Transactional
    public void deveGerarErroQuandoLoginIncorreto() throws Exception {
        // Cenário
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("teste login")
                .senha("teste senha")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/login"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Erro de autenticação"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoSalvarSemCampoObrigatorio() throws Exception{
        // Cenário
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .senha("teste senha")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O login do coordenador deve estar preenchido"));
    }

    @Test
    @Transactional
    public void deveGerarErroQuandoSalvaLoginJaCadastrado() throws Exception {
        // Cenário
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("coordenador1")
                .senha("senha1")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Já existe um coordenador com esse login. Por favor, utilize um login diferente"));
    }

    @Test
    @Transactional
    public void deveEvitarAlteracaoParaNuloOuVazioEmCampoObrigatorio() throws Exception {
        // Cenário
        UUID id = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição PUT
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Validação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_coordenador").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("coordenador1"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarLoginJaCadastrado() throws Exception{
        // Cenário
        UUID id = UUID.fromString("76e295c4-316d-492f-b5c8-862270c8b614");
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("coordenador1")
                .senha("senha1")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição PUT
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Já existe um coordenador com esse login. Por favor, utilize um login diferente"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarCoordenadorNaoExistente() throws Exception{
        // Cenário
        UUID id = UUID.randomUUID();

        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("lonqualquer")
                .senha("senha qualquer")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Verifica status 400 Bad Request
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdVazio() throws Exception{
        // Cenário
        UUID id = null;

        // Ação
        // Constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400
                .andExpect(MockMvcResultMatchers.content().string("ID Inválido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdInexistente() throws Exception{
        // Cenário
        UUID id = UUID.randomUUID();

        // Ação
        // Constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound()) // Espera 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumCoordenador() throws Exception{
        // Cenário
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

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/listarTodos"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Verifica status 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("Nenhum resultado para a busca"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoCadastrarCursoComCoordenadorInvalido() throws Exception{
        // Cenário
        UUID id = UUID.randomUUID();
        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("nome teste")
                .nivel("nivel teste")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        // Ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/cadastrarCurso/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 Bad Request
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarCadastrarEgressoSemCampoObrigatorio() throws Exception{
        // Cenário
        EgressoDTO egressoDTO = EgressoDTO.builder()
                .senha("teste senha")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        // Ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/cadastrarEgresso"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Egresso inválido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoTentarAssociarCursoInvalidoAEgresso() throws Exception{
        // Cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("nome")
                .nivel("nivel")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        // Ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/associarCursoAEgresso/" + id + "/" + UUID.randomUUID()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 Bad Request
                .andExpect(MockMvcResultMatchers.content().string("Não foi possível associar curso a egresso"));
    }

    @Test
    @Transactional
    public void deveHomologarDepoimento() throws Exception{
        // Cenário
        UUID id = UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c");
        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("texto teste")
                .status(Status.AGUARDANDO)
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(depoimentoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/homologarDepoimento/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK
    }

    @Test
    @Transactional
    public void deveCadastrarCurso() throws Exception{
        // Cenário
        UUID id = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");
        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("nome teste")
                .nivel("nivel teste")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/cadastrarCurso/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK
    }

    @Test
    @Transactional
    public void deveCadastrarEgresso() throws Exception{
        // Cenário
        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("egresso teste")
                .email("egressoteste@email.com")
                .senha("senha teste")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/cadastrarEgresso"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK
    }

    @Test
    @Transactional
    public void deveAssociarCursoAEgresso() throws Exception{

        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        UUID idCurso = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");
        CursoEgressoDTO cursoEgressoDTO = CursoEgressoDTO.builder()
                .anoInicio(2020)
                .anoFim(2024)
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoEgressoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/associarCursoAEgresso/" + idEgresso + "/" + idCurso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK
    }

    @Test
    @Transactional
    public void deveVerificarSalvarOCoordenador() throws Exception{
        // Cenário
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("login teste")
                .senha("senha teste")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()); // espera 201 CREATED
    }

    @Test
    @Transactional
    public void deveVerificarAtualizarOCoordenador() throws Exception{
        // Cenário
        UUID id = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("login atualizado")
                .senha("senha atualizada")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_coordenador").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("login atualizado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("senha atualizada"));
    }

    @Test
    @Transactional
    public void deveBuscarPorIdExistente() throws Exception{
        // Cenário
        UUID id = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_coordenador").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("coordenador1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("senha1"));
    }

    @Test
    @Transactional
    public void deveListarTodosOsCoordenadores() throws Exception{
        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/listarTodos"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deveDeletarCoordenador() throws Exception{
        // Cenário
        UUID id = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    public void deveEfetuarLogin() throws Exception{
        // Cenário
        CoordenadorDTO coordenadorDTO = CoordenadorDTO.builder()
                .login("coordenador1")
                .senha("senha1")
                .build();

        // Converte DTO para json
        String json = new ObjectMapper().writeValueAsString(coordenadorDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/login"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
