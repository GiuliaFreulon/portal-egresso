package com.ufma.PortalEgresso.controller;

import com.ufma.PortalEgresso.exception.BuscaVaziaRunTime;
import com.ufma.PortalEgresso.exception.RegraNegocioRunTime;
import com.ufma.PortalEgresso.model.entity.*;
import com.ufma.PortalEgresso.model.entity.DTOs.*;
import com.ufma.PortalEgresso.service.CargoService;
import com.ufma.PortalEgresso.service.CursoService;
import com.ufma.PortalEgresso.service.DiscussaoService;
import com.ufma.PortalEgresso.service.EgressoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/egresso")
@Validated
public class EgressoController {
    @Autowired
    EgressoService egressoService;

    @Autowired
    CargoService cargoService;

    @Autowired
    CursoService cursoService;

    @Autowired
    DiscussaoService discussaoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------------------- AUTENTICAÇÃO ---------------------
    
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody EgressoDTO request) {
//        try {
//            egressoService.efetuarLogin(request.getEmail(), request.getSenha());
//            return ResponseEntity.ok().body(true);
//        } catch (RegraNegocioRunTime e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    // -------------------- endpoints CRUD ---------------------

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody @Valid EgressoDTO request) {
        Egresso egresso = Egresso.builder()
                                .nome(request.getNome())
                                .email(request.getEmail())
                                .senha(passwordEncoder.encode(request.getSenha()))
                                .descricao(request.getDescricao())
                                .foto(request.getFoto())
                                .linkedin(request.getLinkedin())
                                .github(request.getGithub())
                                .curriculo(request.getCurriculo())
                                .build();
        try {
            Egresso salvo = egressoService.salvar(egresso);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    
    }

    @PutMapping(value = "{id}", consumes = {"multipart/form-data"})
    public ResponseEntity atualizar(
            @PathVariable UUID id,
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam(value = "senha", required = false) String senha,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestPart(value = "foto", required = false) MultipartFile foto,
            @RequestPart(value = "curriculo", required = false) MultipartFile curriculo,
            @RequestParam(value = "linkedin", required = false) String linkedin,
            @RequestParam(value = "github", required = false) String github) {
        try {
            // Recupera o egresso existente do banco de dados
            Optional<Egresso> egressoExistenteOptional = egressoService.buscarPorId(id);
            Egresso egressoExistente = egressoExistenteOptional.get();

            if (egressoExistente == null) {
                return ResponseEntity.notFound().build(); // Retorna 404 se o egresso não for encontrado
            }

            // Se uma nova foto foi enviada, atualiza, senão mantém a existente
            String fotoBase64 = (foto != null) ? Base64.getEncoder().encodeToString(foto.getBytes()) : egressoExistente.getFoto();
            // Se um novo currículo foi enviado, atualiza, senão mantém o existente
            String curriculoBase64 = (curriculo != null) ? Base64.getEncoder().encodeToString(curriculo.getBytes()) : egressoExistente.getCurriculo();

            // Atualiza os campos passados no DTO
            if (nome != null && !nome.trim().isEmpty()) {
                egressoExistente.setNome(nome);
            }
            if (email != null && !email.trim().isEmpty()) {
                egressoExistente.setEmail(email);
            }
            if (senha != null && !senha.trim().isEmpty()) {
                egressoExistente.setSenha(passwordEncoder.encode(senha));
            }
            if (descricao != null) {
                egressoExistente.setDescricao(descricao);
            }
            if (foto != null) {
                egressoExistente.setFoto(fotoBase64);
            }
            if (curriculo != null) {
                egressoExistente.setCurriculo(curriculoBase64);
            }
            if (linkedin != null) {
                egressoExistente.setLinkedin(linkedin);
            }
            if (github != null) {
                egressoExistente.setGithub(github);
            }

            // Atualiza o egresso no banco de dados
            Egresso atualizado = egressoService.atualizar(egressoExistente);

            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        try {
            egressoService.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/enviarDepoimento/{id}")
    public ResponseEntity enviarDepoimento(@PathVariable UUID id, @RequestBody DepoimentoDTO request) {
        Egresso egresso = egressoService.buscarPorId(id).get();
        Depoimento depoimento = Depoimento.builder()
                .egresso(egresso)
                .texto(request.getTexto())
                .build();
        try {
            Depoimento salvo = egressoService.enviarDepoimento(depoimento);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/enviarOportunidade/{id}")
    public ResponseEntity enviarOportunidade(@PathVariable UUID id, @RequestBody OportunidadeDTO request) {
        Egresso egresso = egressoService.buscarPorId(id).get();
        Oportunidade oportunidade = Oportunidade.builder()
                .egresso(egresso)
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .build();
        try {
            Oportunidade salvo = egressoService.enviarOportunidade(oportunidade);

            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criarDiscussao/{id}")
    public ResponseEntity criarDiscussao(@PathVariable UUID id, @RequestBody DiscussaoDTO request) {
        Egresso egresso = egressoService.buscarPorId(id).get();
        Discussao discussao = Discussao.builder()
                .egresso(egresso)
                .titulo(request.getTitulo())
                .build();
        try {
            Discussao salvo = egressoService.criarDiscussao(discussao);

            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/enviarMensagem/{idDiscussao}/{idEgresso}")
    public ResponseEntity enviarMensagem(@PathVariable UUID idDiscussao, @PathVariable UUID idEgresso, @RequestBody MensagemDTO request) {
        Egresso egresso = egressoService.buscarPorId(idEgresso).get();
        Discussao discussao = discussaoService.buscarPorId(idDiscussao).get();
        Mensagem mensagem = Mensagem.builder()
                .egresso(egresso)
                .texto(request.getTexto())
                .discussao(discussao)
                .build();

        try {
            Mensagem salvo = egressoService.enviarMensagem(mensagem);

            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -------------------- endpoints de consulta ---------------------

    @GetMapping("/buscarPorCurso/{idCurso}")
    public ResponseEntity buscarPorCurso(@PathVariable UUID idCurso) {
        try {
            Curso curso = cursoService.buscarPorId(idCurso).get();

            List<Egresso> lista = egressoService.buscarPorCurso(curso);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorCargo/{idCargo}")
    public ResponseEntity buscarPorCargo(@PathVariable UUID idCargo) {
        try {
            Cargo cargo = cargoService.buscarPorId(idCargo).get();

            List<Egresso> lista = egressoService.buscarPorCargo(cargo);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/buscarPorNome/{nomeEgresso}")
    public ResponseEntity buscarPorNome(@PathVariable String nomeEgresso) {
        try {
            List<Egresso> lista = egressoService.buscarPorNome(nomeEgresso);
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity listarTodos() {
        try {
            List<Egresso> lista = egressoService.listarTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarNomeIdTodos")
    public ResponseEntity listarNomeIdTodos() {
        try {
            List<EgressoResumoDTO> lista = egressoService.listarNomeIdTodos();
            return ResponseEntity.ok(lista);

        } catch (RegraNegocioRunTime | BuscaVaziaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}
