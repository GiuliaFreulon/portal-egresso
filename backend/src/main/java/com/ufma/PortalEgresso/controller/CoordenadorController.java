package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.entity.DTOs.*;
import com.ufma.PortalEgresso.service.CoordenadorService;
import com.ufma.PortalEgresso.service.CursoService;
import com.ufma.PortalEgresso.service.DepoimentoService;
import com.ufma.PortalEgresso.service.OportunidadeService;
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
    @Autowired
    private CursoService cursoService;
    @Autowired
    private DepoimentoService depoimentoService;
    @Autowired
    private OportunidadeService oportunidadeService;

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

    @PostMapping("/cadastrarEgresso")
    public ResponseEntity cadastrarEgresso(@RequestBody EgressoDTO egressoRequest) {
        try {
            Egresso egresso = Egresso.builder()
                .nome(egressoRequest.getNome())
                .email(egressoRequest.getEmail())
                .senha(egressoRequest.getSenha())
                .build();

            Egresso cadastrado = coordenadorService.cadastrarEgresso(egresso);

            return ResponseEntity.ok().body(cadastrado);
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

    @PostMapping("/associarCursoAEgresso/{idEgresso}/{idCurso}")
    public ResponseEntity associarCursoAEgresso(
            @PathVariable UUID idEgresso,
            @PathVariable UUID idCurso,
            @RequestBody CursoEgressoDTO cursoEgressoRequest) {

        try {
            Egresso egresso = egressoService.buscarPorId(idEgresso).get();
            Curso curso = cursoService.buscarPorId(idCurso).get();

            Curso cursoAtualizado = coordenadorService.associarCursoAEgresso(egresso, curso, cursoEgressoRequest.getAnoInicio(), cursoEgressoRequest.getAnoFim());
            return ResponseEntity.ok().body(cursoAtualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body("Não foi possível associar curso a egresso");
        }
    }

    @PostMapping("/homologarDepoimento/{id}")
    public ResponseEntity homologarDepoimento(@PathVariable UUID id, @RequestBody DepoimentoDTO depoimentoRequest) {
        try {
            Depoimento depoimento = depoimentoService.buscarPorId(id).get();
            Depoimento salvo = coordenadorService.homologarDepoimento(depoimento, depoimentoRequest.getStatus());

            return ResponseEntity.ok().body(salvo);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/homologarOportunidade/{id}")
    public ResponseEntity homologarOportunidade(@PathVariable UUID id, @RequestBody OportunidadeDTO oportunidadeRequest) {
        try {
            Oportunidade oportunidade = oportunidadeService.buscarPorId(id).get();
            Oportunidade salvo = coordenadorService.homologarOportunidade(oportunidade, oportunidadeRequest.getStatus());

            return ResponseEntity.ok().body(salvo);
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

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
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
