package com.ufma.PortalEgresso.controllers;

import java.util.List;
import java.util.UUID;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.Curso;
import com.ufma.PortalEgresso.service.CargoService;
import com.ufma.PortalEgresso.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.model.entity.DTOs.EgressoDTO;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/egressos")
@Validated
public class EgressoController {
    @Autowired
    EgressoService egressoService;

    @Autowired
    CargoService cargoService;

    @Autowired
    CursoService cursoService;

    // -------------------- AUTENTICAÇÃO ---------------------
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody EgressoDTO request) {
        try {
            egressoService.efetuarLogin(request.getEmail(), request.getSenha());
            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody @Valid EgressoDTO request) {
        Egresso egresso = Egresso.builder()
                                .nome(request.getNome())
                                .email(request.getEmail())
                                .senha(request.getSenha())
                                .descricao(request.getDescricao())
                                .foto(request.getFoto())
                                .linkedin(request.getLinkedin())
                                .instagram(request.getInstagram())
                                .curriculo(request.getCurriculo())
                                .build();
        try {
            Egresso salvo = egressoService.salvar(egresso);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody @Valid EgressoDTO request) {
        try {
            // Recupera o egresso existente do banco de dados
            Egresso egressoExistente = egressoService.buscarPorId(id).get();

            if (egressoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o egresso não for encontrado
            }

            // Atualiza os campos passados no DTO
            if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
                egressoExistente.setNome(request.getNome());
            }
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                egressoExistente.setEmail(request.getEmail());
            }
            if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
                egressoExistente.setSenha(request.getSenha());
            }
            if (request.getDescricao() != null) {
                egressoExistente.setDescricao(request.getDescricao());
            }
            if (request.getFoto() != null) {
                egressoExistente.setFoto(request.getFoto());
            }
            if (request.getLinkedin() != null) {
                egressoExistente.setLinkedin(request.getLinkedin());
            }
            if (request.getInstagram() != null) {
                egressoExistente.setInstagram(request.getInstagram());
            }
            if (request.getCurriculo() != null) {
                egressoExistente.setCurriculo(request.getCurriculo());
            }

            // Atualiza o egresso no banco de dados
            Egresso atualizado = egressoService.atualizar(egressoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Egresso egresso = egressoService.buscarPorId(id).get();
            return ResponseEntity.ok(egresso);
        
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorCurso/{idCurso}")
    public ResponseEntity buscarPorCurso(@PathVariable UUID idCurso) {
        try {
            Curso curso = cursoService.buscarPorId(idCurso).get();

            List<Egresso> lista = egressoService.buscarPorCurso(curso);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorCargo/{idCargo}")
    public ResponseEntity buscarPorCargo(@PathVariable UUID idCargo) {
        try {
            Cargo cargo = cargoService.buscarPorId(idCargo).get();

            List<Egresso> lista = egressoService.buscarPorCargo(cargo);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Egresso> lista = egressoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            egressoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
