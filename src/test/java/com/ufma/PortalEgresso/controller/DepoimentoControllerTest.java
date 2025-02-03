package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.DepoimentoDTO;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class DepoimentoControllerTest {
    final static String API = "/api/depoimento";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void deveGerarErroAtualizarDepoimentoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("texto teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(depoimentoDTO);

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
    public void deveGerarErroAoBuscarDepoimentoInexistente() throws Exception {
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
    public void deveGerarErroAoDeletarDepoimentoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("texto teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(depoimentoDTO);

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
    public void deveGerarErroAoNaoEncontrarNenhumDepoimento() throws Exception {
        // Cenário
        Query deleteDepoimento = entityManager.createQuery("Delete from Depoimento");

        deleteDepoimento.executeUpdate();

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
    public void deveSalvarDepoimento() throws Exception {
        // cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("teste texto")
                .data(LocalDate.of(2001, 9, 11))
                .build();

        // converte DTO para json
        String json = objectMapper.writeValueAsString(depoimentoDTO);
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
    public void deveAtualizarDepoimento() throws Exception {
        // cenário
        UUID id = UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c");

        DepoimentoDTO depoimentoDTO = DepoimentoDTO.builder()
                .texto("novo texto")
                .data(LocalDate.of(2020, 9, 11))
                .build();

        // Converte o DTO para JSON
        String json = objectMapper.writeValueAsString(depoimentoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_depoimento").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.texto").value("novo texto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("2020-09-11"));
    }

    @Test
    @Transactional
    public void deveBuscarDepoimentoPorID() throws Exception {
        // cenário
        UUID id = UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c");

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_depoimento").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.texto").value("Depoimento do egresso 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("2024-12-12"));
    }

    @Test
    @Transactional
    public void deveDeletarDepoimento() throws Exception {
        // Cenário
        UUID id = UUID.fromString("88dd072f-4025-4462-880c-61b9ee44857c");


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