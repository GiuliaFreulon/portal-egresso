package com.ufma.PortalEgresso.controllers;

import java.util.List;
import java.util.UUID;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.DTOs.CursoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody CursoDTO request) {
        Curso curso = Curso.builder()
                .nome(request.getNome())
                .nivel(request.getNivel())
                .build();
        try {
            Curso salvo = cursoService.salvar(curso);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody CursoDTO request) {
        try {
            // Recupera o curso existente do banco de dados
            Curso cursoExistente = cursoService.buscarPorId(id).get();

            if (cursoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o curso não for encontrado
            }

            // Atualiza os campos passados no DTO
            if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
                cursoExistente.setNome(request.getNome());
            }
            if (request.getNivel() != null && !request.getNivel().trim().isEmpty()) {
                cursoExistente.setNivel(request.getNivel());
            }

            // Atualiza o curso no banco de dados
            Curso atualizado = cursoService.atualizar(cursoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Curso curso = cursoService.buscarPorId(id).get();
            return ResponseEntity.ok(curso);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Curso> lista = cursoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            cursoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
