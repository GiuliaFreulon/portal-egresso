package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.DTOs.DepoimentoDTO;
import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.service.DepoimentoService;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/depoimento")
public class DepoimentoController {
    @Autowired
    DepoimentoService depoimentoService;
    @Autowired
    private EgressoService egressoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("{id}")
    public ResponseEntity salvar(@PathVariable UUID id, @RequestBody DepoimentoDTO request) {
        Depoimento depoimento = Depoimento.builder()
                .egresso(egressoService.buscarPorId(id).get())
                .texto(request.getTexto())
                .data(request.getData())
                .build();
        try {
            Depoimento salvo = depoimentoService.salvar(depoimento);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody DepoimentoDTO request) {
        try {
            // Recupera o depoimento existente do banco de dados
            Depoimento depoimentoExistente = depoimentoService.buscarPorId(id).get();

            if (depoimentoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o depoimento não for encontrado
            }

            // Atualiza os campos passados no DTO
            if (request.getTexto() != null && !request.getTexto().trim().isEmpty()) {
                depoimentoExistente.setTexto(request.getTexto());
            }
            if (request.getData() != null) {
                depoimentoExistente.setData(request.getData());
            }

            // Atualiza o depoimento no banco de dados
            Depoimento atualizado = depoimentoService.atualizar(depoimentoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Depoimento depoimento = depoimentoService.buscarPorId(id).get();
            return ResponseEntity.ok(depoimento);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorNomeDoCurso/{nomeCurso}")
    public ResponseEntity buscarPorNomeCurso(@PathVariable String nomeCurso) {
        try {
            List<Depoimento> lista = depoimentoService.buscarPorNomeCurso(nomeCurso);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorAno/{ano}")
    public ResponseEntity buscarPorAno(@PathVariable Integer ano) {
        try {
            List<Depoimento> lista = depoimentoService.buscarPorAno(ano);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarRecentes")
    public ResponseEntity buscarRecentes() {
        try {
            List<Depoimento> lista = depoimentoService.buscarRecentes();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Depoimento> lista = depoimentoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            depoimentoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
