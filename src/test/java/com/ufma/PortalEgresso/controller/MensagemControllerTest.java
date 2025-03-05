package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.MensagemDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class MensagemControllerTest {
    final static String API = "/api/mensagem";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void deveGerarErroAoTentarSalvarMensagemVazia() throws Exception {
        // Cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        UUID idDiscussao = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        MensagemDTO mensagemDTO = MensagemDTO.builder()
                .texto("")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(mensagemDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idDiscussao + "/" + idEgresso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("A mensagem deve estar preenchida"));
    }

    @Test
    @Transactional
    public void deveSalvarMensagem() throws Exception {
        // cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        UUID idDiscussao = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        MensagemDTO mensagemDTO = MensagemDTO.builder()
                .texto("texto teste")
                .build();

        // converte DTO para json
        String json = objectMapper.writeValueAsString(mensagemDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idDiscussao + "/" + idEgresso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    public void deveDeletarMensagem() throws Exception {
        // Cenário
        UUID id = UUID.fromString("e3be8f9e-748a-4a30-b429-b1a852f3d464");


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
    public void deveListarMensagensOrdenadas() throws Exception {
        // Cenário
        UUID idDiscussao = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/" + idDiscussao))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
