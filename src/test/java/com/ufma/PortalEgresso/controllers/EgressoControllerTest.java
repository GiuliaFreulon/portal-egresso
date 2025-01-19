package com.ufma.PortalEgresso.controllers;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.service.CargoService;
import com.ufma.PortalEgresso.service.CursoService;
import com.ufma.PortalEgresso.service.EgressoService;

@WebMvcTest(controllers = EgressoController.class)
@AutoConfigureMockMvc
public class EgressoControllerTest {
    final static String API = "/api/egressos";
    @Autowired
    MockMvc mvc;

    @MockitoBean
    EgressoService service;

    @MockitoBean
    CargoService cargoService;

    @MockitoBean
    CursoService cursoService;

    @Test
    public void deveAutenticarEgresso() throws Exception{
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                                .email("teste@teste.com")
                                .senha("teste senha").build();
        Mockito.when(service.efetuarLogin(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
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
    public void deveGerarErroQuandoLoginIncorreto() throws Exception{
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                                .email("teste@teste.com")
                                .senha("teste senha").build();
        Mockito.when(service.efetuarLogin(Mockito.anyString(), Mockito.anyString())).thenThrow(new RegraNegocioRunTime("Erro de autenticação"));
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
    public void deveSalvarEgresso() throws Exception{
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                                .email("teste@teste.com")
                                .senha("teste senha").build();
        // reposta que será mock
        // egresso que vira o body da resposta
        Egresso egresso = Egresso.builder().id_egresso(UUID.randomUUID())
                            .nome("teste")
                            .email("teste@teste.com")
                            .senha("teste senha").build();
        Mockito.when(service.salvar(Mockito.any(Egresso.class))).thenReturn(egresso);
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
    public void deveGerarErroQuandoSalvaEmailJaCadastrado() throws Exception{
        // cenário
        // dto para virar json
        // body da requisição
        EgressoDTO egressoDTO = EgressoDTO.builder().nome("teste")
                                .email("teste@teste.com")
                                .senha("teste senha")
                                .build();
        Mockito.when(service.salvar(Mockito.any(Egresso.class))).
                                thenThrow(new RegraNegocioRunTime("O e-mail já está cadastrado. Por favor, utilize um e-mail diferente"));
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
    public void deveAtualizarEgresso() throws Exception {
        // cenário
        UUID id = UUID.randomUUID();

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("Novo Nome")
                .email("novoemail@teste.com")
                .senha("nova senha")
                .descricao("Nova descrição")
                .build();

        Egresso egressoExistente = Egresso.builder()
                .id_egresso(id)
                .nome("Nome Antigo")
                .email("emailantigo@teste.com")
                .senha("senha antiga")
                .descricao("Descrição antiga")
                .build();

        Egresso egressoAtualizado = Egresso.builder()
                .id_egresso(id)
                .nome("Novo Nome")
                .email("novoemail@teste.com")
                .senha("nova senha")
                .descricao("Nova descrição")
                .build();

        // mocks
        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(egressoExistente));
        Mockito.when(service.atualizar(Mockito.any(Egresso.class))).thenReturn(egressoAtualizado);

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
    public void deveGerarErroAtualizarEgressoInvalido() throws Exception {
        // Cenário
        UUID id = UUID.randomUUID();

        EgressoDTO egressoDTO = EgressoDTO.builder()
                .nome("Nome qualquer")
                .email("emailqualquer@teste.com")
                .senha("senha qualquer")
                .build();

        Egresso egressoExistente = Egresso.builder()
                .id_egresso(id)
                .nome("Nome Antigo")
                .email("emailantigo@teste.com")
                .senha("senha antiga")
                .build();

        // Configurações dos mocks
        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(egressoExistente));
        Mockito.when(service.atualizar(Mockito.any(Egresso.class)))
                .thenThrow(new RegraNegocioRunTime("Erro de negócio ao atualizar o egresso."));

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
                .andExpect(MockMvcResultMatchers.content().string("Erro de negócio ao atualizar o egresso."));
}

    @Test
    public void deveBuscarEgressoPorID() throws Exception {
        // cenário
        UUID id = UUID.randomUUID();
        Egresso egresso = Egresso.builder()
                                .id_egresso(id)
                                .nome("teste")
                                .email("teste@teste.com")
                                .senha("teste senha")
                                .build();
        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(egresso));

        // ação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/") + id);                                            

        // verificação
        mvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk()) // Espera 200 OK
        .andExpect(MockMvcResultMatchers.jsonPath("$.id_egresso").value(id.toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("teste"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@teste.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("teste senha"));
    }

    @Test
    public void deveGerarErroQuandoBuscaEgressoInexistente() throws Exception{
        // cenário
        UUID id = UUID.randomUUID();
        Mockito.when(service.buscarPorId(id)).thenThrow(new RegraNegocioRunTime("ID não encontrado"));
   
        // ação
        // constrói a requisição GET
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/buscarPorId/" + id));
    
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound()) // Espera 404 Not Found
                            .andExpect(MockMvcResultMatchers.content().string("ID não encontrado")); 
    }

    @Test
    public void deveDeletarEgresso(){

    }

    @Test
    public void deveGerarErroAoDeletarEgressoInexistente(){

    }

    @Test
    public void deveBuscarEgressosPorCurso(){

    }

    @Test
    public void deveGerarErroAoBuscarEgressosPorCursoInvalido(){

    }

    @Test
    public void deveBuscarEgressosPorCargo(){

    }

    @Test
    public void deveGerarErroAoBuscarEgressosPorCargoInvalido(){

    }

    @Test
    public void deveListarTodos() throws Exception{

    }

    @Test
    public void deveGerarErroAoListarQuandoNaoExistemEgressos(){

    }
}
