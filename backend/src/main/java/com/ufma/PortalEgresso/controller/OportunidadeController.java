package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.DTOs.OportunidadeDTO;
import com.ufma.PortalEgresso.model.entity.Oportunidade;
import com.ufma.PortalEgresso.service.EgressoService;
import com.ufma.PortalEgresso.service.OportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/oportunidade")
public class OportunidadeController {
    @Autowired
    private OportunidadeService oportunidadeService;
    @Autowired
    private EgressoService egressoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("{idEgresso}")
    public ResponseEntity salvar(@PathVariable UUID idEgresso, @RequestBody OportunidadeDTO request) {
        Oportunidade oportunidade = Oportunidade.builder()
                .egresso(egressoService.buscarPorId(idEgresso).get())
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .status(request.getStatus())
                .build();
        try {
            Oportunidade salvo = oportunidadeService.salvar(oportunidade);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody OportunidadeDTO request) {
        try {
            // Recupera a discussão existente do banco de dados
            Oportunidade oportunidadeExistente = oportunidadeService.buscarPorId(id).get();

            if (oportunidadeExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se a discussão não for encontrada
            }

            // Atualiza os campos passados no DTO
            if (request.getTitulo() != null && !request.getTitulo().trim().isEmpty()) {
                oportunidadeExistente.setTitulo(request.getTitulo());
            }

            if (request.getDescricao() != null && !request.getDescricao().trim().isEmpty()) {
                oportunidadeExistente.setDescricao(request.getDescricao());
            }

            if (request.getStatus() != null) {
                oportunidadeExistente.setStatus(request.getStatus());
            }

            // Atualiza a discussão no banco de dados
            Oportunidade atualizado = oportunidadeService.atualizar(oportunidadeExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Oportunidade oportunidade = oportunidadeService.buscarPorId(id).get();
            return ResponseEntity.ok(oportunidade);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Oportunidade> lista = oportunidadeService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            oportunidadeService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
