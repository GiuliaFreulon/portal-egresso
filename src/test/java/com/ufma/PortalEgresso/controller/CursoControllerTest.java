package com.ufma.PortalEgresso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.CursoDTO;
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
public class CursoControllerTest {
    final static String API = "/api/curso";
    @Autowired
    MockMvc mvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void deveGerarErroAoSalvarSemCampoObrigatorio() throws Exception{
        // cenário
        UUID idCoordenador = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        CursoDTO cursoDTO = CursoDTO.builder()
                .nivel("nivel teste")
                .build();

        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idCoordenador))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O nome do curso deve estar preenchido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAtualizarCursoInexistente() throws Exception {
        // cenário
        UUID id = UUID.randomUUID();

        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("nome teste")
                .nivel("nivel teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

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
    public void deveGerarErroAoBuscarCursoInexistente() throws Exception{
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
    public void deveGerarErroAoBuscarPorIdVazio() throws Exception{
        // cenário
        UUID id = null;

        // ação
        // constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 404 Not Found
                .andExpect(MockMvcResultMatchers.content().string("ID Inválido"));
    }

    @Test
    @Transactional
    public void deveGerarErroAoDeletarCursoInexistente() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("nome teste")
                .nivel("nivel teste")
                .build();


        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

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
    public void deveGerarErroAoNaoEncontrarNenhumCurso() throws Exception {
        // Cenário
        Query deleteCursoEgresso = entityManager.createQuery("Delete from CursoEgresso");
        Query deleteCurso = entityManager.createQuery("Delete from Curso ");

        deleteCursoEgresso.executeUpdate();
        deleteCurso.executeUpdate();

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
    public void deveSalvarCurso() throws Exception{
        // Cenário
        UUID idCoordenador = UUID.fromString("7e12d990-6d73-47d1-9e62-8e84201417a4");

        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("teste nome")
                .nivel("teste nivel")
                .build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(cursoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/" + idCoordenador))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //ação e verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()); // espera 201 CREATED
    }

    @Test
    @Transactional
    public void deveAtualizarCurso() throws Exception {
        // cenário
        UUID id = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");

        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("novo nome")
                .nivel("novo nivel")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_curso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("novo nome"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nivel").value("novo nivel"));
    }

    @Test
    @Transactional
    public void deveEvitarAtualizarParaNuloOuVazioEmCampoObrigatorio() throws Exception {
        // cenário
        UUID id = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");

        CursoDTO cursoDTO = CursoDTO.builder()
                .nome("")
                .build();

        // Converte o DTO para JSON
        String json = new ObjectMapper().writeValueAsString(cursoDTO);

        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_curso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Curso 1"));
    }

    @Test
    @Transactional
    public void deveBuscarCursoPorID() throws Exception {
        // cenário
        UUID id = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);

        // verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_curso").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Curso 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nivel").value("Pos-Graduacao"));
    }

    @Test
    @Transactional
    public void deveDeletarCurso() throws Exception {
        // Cenário
        UUID id = UUID.fromString("0157ecab-4fb7-42b4-91ff-be4db8c759ce");


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
    public void deveListarTodos() throws Exception{
        // Ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/listarTodos"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
