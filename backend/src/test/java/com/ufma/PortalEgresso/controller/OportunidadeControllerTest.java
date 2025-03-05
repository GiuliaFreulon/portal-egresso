package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.OportunidadeDTO;
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
public class OportunidadeControllerTest {
    final static String API = "/api/oportunidade";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void deveGerarErroAtualizarOportunidadeInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        OportunidadeDTO oportunidadeDTO = OportunidadeDTO.builder()
                .titulo("titulo teste")
                .descricao("descricao teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(oportunidadeDTO);

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
    public void deveGerarErroAoNaoEncontrarNenhumaOportunidade() throws Exception {
        // Cenário
        Query deleteOportunidade = entityManager.createQuery("Delete from Oportunidade");

        deleteOportunidade.executeUpdate();

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
    public void deveSalvarOportunidade() throws Exception {
        // cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");

        OportunidadeDTO oportunidadeDTO = OportunidadeDTO.builder()
                .titulo("titulo teste")
                .descricao("descricao teste")
                .build();

        // converte DTO para json
        String json = objectMapper.writeValueAsString(oportunidadeDTO);
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
    public void deveAtualizarOportunidade() throws Exception {
        // cenário
        UUID id = UUID.fromString("d631d7e2-731e-43fc-81fa-c3d62f05c10b");

        OportunidadeDTO oportunidadeDTO = OportunidadeDTO.builder()
                .titulo("titulo novo")
                .descricao("descricao nova")
                .build();

        // Converte o DTO para JSON
        String json = objectMapper.writeValueAsString(oportunidadeDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_oportunidade").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("titulo novo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("descricao nova"));
    }

    @Test
    @Transactional
    public void deveBuscarOportunidadePorID() throws Exception {
        // cenário
        UUID id = UUID.fromString("d631d7e2-731e-43fc-81fa-c3d62f05c10b");

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_oportunidade").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Desenvolvedor Java Júnior"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Vaga para iniciantes em desenvolvimento"));
    }

    @Test
    @Transactional
    public void deveDeletarOportunidade() throws Exception {
        // Cenário
        UUID id = UUID.fromString("d631d7e2-731e-43fc-81fa-c3d62f05c10b");


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
