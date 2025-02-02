package com.ufma.PortalEgresso.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.service.CargoService;
import com.ufma.PortalEgresso.service.CursoService;
import com.ufma.PortalEgresso.service.EgressoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class EgressoControllerTest {
    final static String API = "/api/egressos";
    @Autowired
    MockMvc mvc;

    @Autowired
    EgressoService service;

    @Autowired
    CargoService cargoService;

    @Autowired
    CursoService cursoService;

    @Test
    @Transactional
    public void deveGerarErroQuandoLoginIncorreto() throws Exception{
        // cenário
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                .email("teste@teste.com")
                .senha("teste senha").build();
        // converte DTO para json
        String json = new ObjectMapper().writeValueAsString(egressoDTO);
        // ação
        // constrói a requisição POST
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/login"))
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("Erro de autenticação"));
    }

    @Test
    @Transactional
    public void deveGerarErroQuandoSalvaEmailJaCadastrado() throws Exception{
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
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Espera 400 BadRequest
                .andExpect(MockMvcResultMatchers.content().string("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente"));
    }

    @Test
    @Transactional
    public void deveGerarErroAtualizarEgressoInvalido() throws Exception {
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
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Verifica status 400 Bad Request
                .andExpect(MockMvcResultMatchers.content().string("ID não encontrado"));
    }

    @Test
    @Transactional
    public void deveGerarErroQuandoBuscaEgressoInexistente() throws Exception{
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
    public void deveGerarErroAoDeletarEgressoInexistente(){
        UUID id = UUID.randomUUID();

    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarEgressosPorCursoInvalido(){

    }

    @Test
    @Transactional
    public void deveGerarErroAoBuscarEgressosPorCargoInvalido(){

    }

    @Test
    @Transactional
    public void deveGerarErroAoListarQuandoNaoExistemEgressos(){

    }

    @Test
    @Transactional
    public void deveAutenticarEgresso() throws Exception{
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
                                                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                                .content(json);
        //ação e verificação
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()); // espera 200 OK                                  
    }

    @Test
    @Transactional
    public void deveSalvarEgresso() throws Exception{
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
                                                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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
    public void deveDeletarEgresso(){

    }

    @Test
    @Transactional
    public void deveBuscarEgressosPorCurso(){

    }

    @Test
    @Transactional
    public void deveBuscarEgressosPorCargo(){

    }

    @Test
    @Transactional
    public void deveListarTodos() throws Exception{

    }
}
