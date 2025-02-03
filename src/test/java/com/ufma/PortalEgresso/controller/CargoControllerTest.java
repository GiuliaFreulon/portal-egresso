package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.CargoDTO;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
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
public class CargoControllerTest {

    final static String API = "/api/cargo";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoSalvarSemCampoObrigatorio() throws Exception{
        // Cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        CargoDTO cargoDTO = CargoDTO.builder()
                .descricao("descricao teste")
                .anoInicio(2021)
                .build();

        // Converte DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cargoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idEgresso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O local do cargo deve estar preenchido"));
    }

    @Test
    @Transactional
    public void deveEvitarAtualizarParaNuloOuVazioEmCampoObrigatorio() throws Exception{
        UUID idCargo = UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052");
        CargoDTO cargoDTO = CargoDTO.builder()
                .descricao("")
                .build();

        // Converte DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cargoDTO);

        // Ação
        // Constrói a requisição PUT
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + idCargo))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //validação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_cargo").value(idCargo.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Cargo 1"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoAtualizarCargoInexistente() throws Exception{
        // Cenário
        UUID idEgresso = UUID.randomUUID();
        EgressoDTO egressoDTO = EgressoDTO.builder()
                .descricao("")
                .build();

        // Converte DTO para JSON
        String json = new ObjectMapper().writeValueAsString(egressoDTO);

        // Ação
        // Constrói a requisição PUT
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + idEgresso))
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
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID Inválido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarCargoInexistente() throws Exception{
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
    public void deveGerarErroAoNaoEncontrarNenhumCargo() throws Exception {
        // Cenário
        Query deleteCargo = entityManager.createQuery("Delete from Cargo ");

        deleteCargo.executeUpdate();

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
    public void deveSalvarCargo() throws Exception{
        // Cenário
        UUID idEgresso = UUID.fromString("e2ff521f-168e-4337-a9e8-2109ccee0531");
        CargoDTO cargoDTO = CargoDTO.builder()
                .descricao("descricao teste")
                .anoInicio(2021)
                .local("local teste")
                .build();

        // Converte DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cargoDTO);

        // Ação
        // Constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idEgresso))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()); // espera 201 CREATED
    }

    @Test
    @Transactional
    public void deveAtualizarCargo() throws Exception {
        // Cenário
        UUID idCargo = UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052");
        CargoDTO cargoDTO = CargoDTO.builder()
                .descricao("descricao teste")
                .anoInicio(2021)
                .local("local teste")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cargoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + idCargo))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_cargo").value(idCargo.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("descricao teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoInicio").value(2021))
                .andExpect(MockMvcResultMatchers.jsonPath("$.local").value("local teste"));
    }

    @Test
    @Transactional
    public void deveBuscarCargoPorID() throws Exception {
        // Cenário
        UUID id = UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052");

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_cargo").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Cargo 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoInicio").value(2010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.local").value("Local 1"));
    }

    @Test
    @Transactional
    public void deveListarTodos() throws Exception{
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
    public void deveDeletarCargo() throws Exception {
        // Cenário
        UUID id = UUID.fromString("67c16f9b-306e-4f89-ad69-1918146f6052");

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}
