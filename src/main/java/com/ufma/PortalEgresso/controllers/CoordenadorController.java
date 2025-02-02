package com.ufma.PortalEgresso.controllers;

import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.Cargo;
import com.ufma.PortalEgresso.model.entity.Coordenador;
import com.ufma.PortalEgresso.model.entity.DTOs.*;
import com.ufma.PortalEgresso.model.entity.Depoimento;
import com.ufma.PortalEgresso.model.entity.Egresso;
import com.ufma.PortalEgresso.service.CoordenadorService;
import com.ufma.PortalEgresso.service.EgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coordenador")
public class CoordenadorController {
    @Autowired
    CoordenadorService coordenadorService;
    @Autowired
    private EgressoService egressoService;

    // -------------------- AUTENTICAÇÃO ---------------------
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CoordenadorDTO request) {
        try {
            coordenadorService.efetuarLogin(request.getLogin(), request.getSenha());
            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Homologar Egresso
    @PostMapping("/homologar")
    public ResponseEntity homologarEgresso(@RequestBody EgressoDTO egressoRequest) {
        try {
            Egresso egresso = Egresso.builder()
                .nome(egressoRequest.getNome())
                .email(egressoRequest.getEmail())
                .senha(egressoRequest.getSenha())
                .descricao(egressoRequest.getDescricao())
                .foto(egressoRequest.getFoto())
                .linkedin(egressoRequest.getLinkedin())
                .instagram(egressoRequest.getInstagram())
                .curriculo(egressoRequest.getCurriculo())
                .build();

            Egresso homologado = coordenadorService.homologarEgresso(egresso);

            return ResponseEntity.ok().body(homologado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -------------------- RESPONSABILIDADES ---------------------

    @PostMapping("/cadastrarCurso/{id}")
    public ResponseEntity cadastrarCurso(@PathVariable UUID id, @RequestBody CursoDTO cursoRequest) {
        try {
            Coordenador coordenador = coordenadorService.buscarPorId(id).get();
            coordenadorService.cadastrarCurso(
                    coordenador.getLogin(),
                    cursoRequest.getNome(),
                    cursoRequest.getNivel());

            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/associarCargoAEgresso/{id}")
    public ResponseEntity associarCargoAEgresso(@PathVariable UUID id, @RequestBody CargoDTO cargoRequest) {
        try {
            Egresso egresso = egressoService.buscarPorId(id).get();
            Cargo cargo = Cargo.builder()
                    .descricao(cargoRequest.getDescricao())
                    .local(cargoRequest.getLocal())
                    .anoInicio(cargoRequest.getAnoInicio())
                    .anoFim(cargoRequest.getAnoFim()).build();

            coordenadorService.associarCargoAEgresso(egresso, cargo);
            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/associarDepoimentoAEgresso/{id}")
    public ResponseEntity associarDepoimentoAEgresso(@PathVariable UUID id, @RequestBody DepoimentoDTO depoimentoRequest) {
        try {
            Egresso egresso = egressoService.buscarPorId(id).get();
            Depoimento depoimento = Depoimento.builder()
                    .texto(depoimentoRequest.getTexto())
                    .data(depoimentoRequest.getData()).build();

            coordenadorService.associarDepoimentoAEgresso(egresso, depoimento);
            return ResponseEntity.ok().body(true);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody CoordenadorDTO request) {
        Coordenador coordenador = Coordenador.builder()
                .login(request.getLogin())
                .senha(request.getSenha())
                .tipo(request.getTipo())
                .build();
        try {
            Coordenador salvo = coordenadorService.salvar(coordenador);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable UUID id, @RequestBody CoordenadorDTO request) {
        try {
            // Recupera o coordenador existente do banco de dados
            Coordenador coordenadorExistente = coordenadorService.buscarPorId(id).get();

            if (coordenadorExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o coordenador não for encontrado
            }

            // Atualiza os campos passados no DTO
            if (request.getLogin() != null && !request.getLogin().trim().isEmpty()) {
                coordenadorExistente.setLogin(request.getLogin());
            }
            if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
                coordenadorExistente.setSenha(request.getSenha());
            }
            if (request.getTipo() != null && !request.getTipo().trim().isEmpty()) {
                coordenadorExistente.setTipo(request.getTipo());
            }

            // Atualiza o coordenador no banco de dados
            Coordenador atualizado = coordenadorService.atualizar(coordenadorExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity buscarPorId(@PathVariable UUID id) {
        try {
            Coordenador coordenador = coordenadorService.buscarPorId(id).get();
            return ResponseEntity.ok(coordenador);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Coordenador> lista = coordenadorService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            coordenadorService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
