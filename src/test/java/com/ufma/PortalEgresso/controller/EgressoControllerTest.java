package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ufma.PortalEgresso.model.entity.DTOs.DepoimentoDTO;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.service.EgressoService;
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

import java.time.LocalDate;
import java.util.UUID;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EgressoControllerTest {
    final static String API = "/api/egresso";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroQuandoLoginIncorreto() throws Exception {
        // cenário
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                .email("teste@teste.com")
                .senha("teste senha").build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/login"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Erro de autenticação"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoSalvarSemCampoObrigatorio() throws Exception{
        // cenário
        EgressoDTO egressoDTO = EgressoDTO.builder()
                .email("teste@teste.com")
                .senha("teste senha")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O nome do egresso deve estar preenchido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoSalvarEmailJaCadastrado() throws Exception {
        // cenário
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                .email("egresso1@email.com")
                .senha("teste senha")
                .build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarEmailJaCadastrado() throws Exception {
        // cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("teste")
                .email("egresso2@email.com")
                .senha("teste senha")
                .build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente"));
    }

    @Test
    @Transactional
    public void deveGerarErroAtualizarEgressoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("Nome qualquer")
                .email("emailqualquer@teste.com")
                .senha("senha qualquer")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

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
    public void deveGerarErroAoBuscarEgressoInexistente() throws Exception {
        // cenário
        UUID id = UUID.randomUUID();

        // ação
        // constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound()) // Espera 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorIdVazio() throws Exception {
        // cenário
        UUID id = null;

        // ação
        // constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400
                .andExpect(MockMvcResultMatchers.content().string("ID Inválido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoDeletarEgressoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("Nome qualquer")
                .email("emailqualquer@teste.com")
                .senha("senha qualquer")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API.concat("/" + id))
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
    public void deveGerarErroAoBuscarEgressosPorCursoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorCurso/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Verifica status 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));

    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarPorNomeInexistente() throws Exception {
        // Cenário
        String nome = "nome inexistente";


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorNome/" + nome))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Verifica status 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("Nenhum resultado para a busca"));

    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarEgressosPorCargoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorCargo/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Verifica status 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));

    }

    @Test
    @Transactional
    public void deveGerarErroAoNaoEncontrarNenhumEgresso() throws Exception {
        // Cenário
        Query deleteCargo = entityManager.createQuery("Delete from Cargo");
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso");
        Query deleteEgresso = entityManager.createQuery("Delete from Egresso");

        deleteCargo.executeUpdate();
        deleteDepoimento.executeUpdate();
        deleteCursoEgresso.executeUpdate();
        deleteEgresso.executeUpdate();

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
    public void deveAutenticarEgresso() throws Exception {
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                .email("egresso1@email.com")
                .senha("senha1").build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/login"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //ação e verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK                                  
    }

    @Test
    @Transactional
    public void deveSalvarEgresso() throws Exception {
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                .email("teste@teste.com")
                .senha("teste senha").build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //ação e verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()); // espera 201 CREATED
    }

    @Test
    @Transactional
    public void deveAtualizarEgresso() throws Exception {
        // cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("Novo Nome")
                .email("novoemail@teste.com")
                .senha("nova senha")
                .descricao("Nova descrição")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_egresso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Novo Nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("novoemail@teste.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("nova senha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Nova descrição"));
    }

    @Test
    @Transactional
    public void deveEvitarAtualizarParaNuloOuVazioEmCampoObrigatorio() throws Exception{
        // cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição PUT
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //validação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_egresso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Egresso 1"));
    }

    @Test
    @Transactional
    public void deveBuscarEgressoPorID() throws Exception {
        // cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_egresso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Egresso 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("egresso1@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("senha1"));
    }

    @Test
    @Transactional
    public void deveBuscarEgressoPorNome() throws Exception {
        // Cenário
        String nome = "Egresso 1";


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorNome/" + nome))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deveDeletarEgresso() throws Exception {
        // Cenário
        UUID id = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");


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
    public void deveBuscarEgressosPorCurso() throws Exception {
        // Cenário
        UUID id = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorCurso/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deveBuscarEgressosPorCargo() throws Exception {
        // Cenário
        UUID id = UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052");


        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorCargo/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deveListarTodos() throws Exception {
        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/listarTodos"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}