package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.DiscussaoDTO;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DiscussaoControllerTest {
    final static String API = "/api/discussao";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void deveGerarErroAtualizarDiscussaoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        DiscussaoDTO discussaoDTO = DiscussaoDTO.builder()
                .titulo("titulo teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(discussaoDTO);

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
    public void deveGerarErroAoNaoEncontrarNenhumaDiscussao() throws Exception {
        // Cenário
        Query deleteMensagem = entityManager.createQuery("Delete from Mensagem");
        Query deleteDiscussao = entityManager.createQuery("Delete from Discussao");

        deleteMensagem.executeUpdate();
        deleteDiscussao.executeUpdate();

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
    public void deveSalvarDiscussao() throws Exception {
        // cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        DiscussaoDTO discussaoDTO = DiscussaoDTO.builder()
                .titulo("titulo teste")
                .build();

        // converte DTO para json
        String json = objectMapper.writeValueAsString(discussaoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idEgresso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    public void deveAtualizarDiscussao() throws Exception {
        // cenário
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");


        DiscussaoDTO discussaoDTO = DiscussaoDTO.builder()
                .titulo("novo titulo")
                .build();

        // Converte o DTO para JSON
        String json = objectMapper.writeValueAsString(discussaoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_discussao").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("novo titulo"));
    }

    @Test
    @Transactional
    public void deveBuscarDiscussaoPorID() throws Exception {
        // cenário
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_discussao").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Discussão sobre estágios"));
    }

    @Test
    @Transactional
    public void deveDeletarDiscussao() throws Exception {
        // Cenário
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");


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
