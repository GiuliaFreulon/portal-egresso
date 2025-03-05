package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.DTOs.DiscussaoDTO;
import com.ufma.PortalEgresso.model.entity.Discussao;
import com.ufma.PortalEgresso.service.DiscussaoService;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/discussao")
public class DiscussaoController {
    @Autowired
    private DiscussaoService discussaoService;
    @Autowired
    private EgressoService egressoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("{idEgresso}")
    public ResponseEntity salvar(@PathVariable UUID idEgresso, @RequestBody DiscussaoDTO request) {
        Discussao discussao = Discussao.builder()
                .egresso(egressoService.buscarPorId(idEgresso).get())
                .titulo(request.getTitulo())
                .build();
        try {
            Discussao salvo = discussaoService.salvar(discussao);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody DiscussaoDTO request) {
        try {
            // Recupera a discussão existente do banco de dados
            Discussao discussaoExistente = discussaoService.buscarPorId(id).get();

            if (discussaoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se a discussão não for encontrada
            }

            // Atualiza os campos passados no DTO
            if (request.getTitulo() != null && !request.getTitulo().trim().isEmpty()) {
                discussaoExistente.setTitulo(request.getTitulo());
            }

            // Atualiza a discussão no banco de dados
            Discussao atualizado = discussaoService.atualizar(discussaoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Discussao discussao = discussaoService.buscarPorId(id).get();
            return ResponseEntity.ok(discussao);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Discussao> lista = discussaoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            discussaoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
